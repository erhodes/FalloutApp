package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.PlayerCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Desktop-only SQL persistence for [Encounter]s, backed by SQLite via Exposed.
 *
 * Each encounter is stored as a single row: [Encounters.id], [Encounters.name], and a
 * [Encounters.data] JSON blob holding the list of enemy [Character]s. The deeply nested,
 * polymorphic Character graph is (de)serialized with the same [Json] config used by
 * [CharacterDataSource], so the full enemy state round-trips without a normalized schema.
 */
class EncounterDataSource {

    private object Encounters : Table("encounters") {
        val id = varchar("id", 64)
        val name = text("name")
        val data = text("data") // JSON List<Character>, enemies only
        override val primaryKey = PrimaryKey(id)
    }

    private val json: Json = Json {
        serializersModule = DataManager.serializerModule
        ignoreUnknownKeys = true
        classDiscriminator = "type"
    }

    private val database: Database by lazy { connect() }

    private fun connect(): Database {
        val homeDir = System.getProperty("user.home")
        val appDir = Path("$homeDir/.falloutapp")
        if (!SystemFileSystem.exists(appDir)) {
            SystemFileSystem.createDirectories(appDir)
        }
        val db = Database.connect(
            url = "jdbc:sqlite:$homeDir/.falloutapp/encounters.db",
            driver = "org.sqlite.JDBC"
        )
        transaction(db) { SchemaUtils.create(Encounters) }
        return db
    }

    /** Persist [encounter], filtering out any [PlayerCharacter]s so only enemies are stored. */
    suspend fun save(encounter: Encounter) = withContext(Dispatchers.IO) {
        val enemies: List<Character> = encounter.characters.filterNot { it is PlayerCharacter }
        val payload = json.encodeToString(enemies)
        transaction(database) {
            // Upsert via delete + insert keyed on the encounter's stable id.
            Encounters.deleteWhere { Encounters.id eq encounter.id }
            Encounters.insert {
                it[id] = encounter.id
                it[name] = encounter.name
                it[data] = payload
            }
        }
    }

    suspend fun load(id: String): Encounter? = withContext(Dispatchers.IO) {
        transaction(database) {
            Encounters.selectAll().where { Encounters.id eq id }
                .singleOrNull()
                ?.let { rowToEncounter(it) }
        }
    }

    suspend fun loadAll(): List<Encounter> = withContext(Dispatchers.IO) {
        transaction(database) {
            Encounters.selectAll().map { rowToEncounter(it) }
        }
    }

    suspend fun delete(id: String) = withContext(Dispatchers.IO) {
        transaction(database) {
            Encounters.deleteWhere { Encounters.id eq id }
        }
    }

    private fun rowToEncounter(row: ResultRow): Encounter {
        val encounter = Encounter(name = row[Encounters.name], id = row[Encounters.id])
        json.decodeFromString<List<Character>>(row[Encounters.data])
            .forEach { encounter.addCharacter(it) }
        return encounter
    }
}
