package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.campaign.Location
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
 * Desktop-only SQL persistence for [Location]s, backed by SQLite via Exposed.
 *
 * Each location is stored as a single row: [Locations.id], [Locations.name], and a [Locations.data]
 * JSON blob holding the whole [Location]. Unlike [EncounterDataSource], whose blob holds only the
 * enemies, the blob here is the complete object, so the subtype and any subtype-specific fields
 * (a [com.erhodes.falloutapp.model.campaign.Settlement]'s food and population) round-trip without a
 * normalized schema. That means [Locations.name] is duplicated between column and blob; both are
 * written from the same object and the blob is the authority on read, so they can't drift. The
 * column exists because the location list only needs an id and a name.
 */
class LocationDataSource {

    private object Locations : Table("locations") {
        val id = varchar("id", 64)
        val name = text("name")
        val data = text("data") // JSON Location (basic_location | settlement)
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
            url = "jdbc:sqlite:$homeDir/.falloutapp/locations.db",
            driver = "org.sqlite.JDBC"
        )
        transaction(db) { SchemaUtils.create(Locations) }
        return db
    }

    suspend fun save(location: Location) = withContext(Dispatchers.IO) {
        // Encoded against the sealed base so the "type" discriminator is written and subtypes
        // survive the round trip.
        val payload = json.encodeToString<Location>(location)
        transaction(database) {
            // Upsert via delete + insert keyed on the location's stable id.
            Locations.deleteWhere { Locations.id eq location.id }
            Locations.insert {
                it[id] = location.id
                it[name] = location.name
                it[data] = payload
            }
        }
    }

    suspend fun load(id: String): Location? = withContext(Dispatchers.IO) {
        transaction(database) {
            Locations.selectAll()
                .where { Locations.id eq id }
                .singleOrNull()
                ?.let { rowToLocation(it) }
        }
    }

    suspend fun loadAll(): List<Location> = withContext(Dispatchers.IO) {
        transaction(database) {
            Locations.selectAll().map { rowToLocation(it) }
        }
    }

    suspend fun delete(id: String) = withContext(Dispatchers.IO) {
        transaction(database) {
            Locations.deleteWhere { Locations.id eq id }
        }
        Unit
    }

    private fun rowToLocation(row: ResultRow): Location =
        json.decodeFromString<Location>(row[Locations.data])
}
