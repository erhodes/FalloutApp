package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.campaign.Campaign
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
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Desktop-only SQL persistence for [Campaign] data, backed by SQLite via Exposed.
 *
 * Each player character is stored as a single row keyed by the composite primary key
 * ([CampaignCharacters.ownerId], [CampaignCharacters.campaignId]) — one character per owner per
 * campaign, matching how [Campaign.playerCharacters] is keyed. The deeply nested, polymorphic
 * Character graph is (de)serialized into the [CampaignCharacters.data] JSON blob with the same
 * [Json] config used by [CharacterDataSource] and [EncounterDataSource], so the full character
 * state round-trips without a normalized schema.
 */
class CampaignDataSource {

    private object CampaignCharacters : Table("campaign_characters") {
        val ownerId = varchar("owner_id", 64)
        val campaignId = varchar("campaign_id", 64)
        val data = text("data") // JSON PlayerCharacter
        override val primaryKey = PrimaryKey(ownerId, campaignId)
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
            url = "jdbc:sqlite:$homeDir/.falloutapp/campaign.db",
            driver = "org.sqlite.JDBC"
        )
        transaction(db) { SchemaUtils.create(CampaignCharacters) }
        return db
    }

    /** Persist [character] as the entry for its owner in the campaign identified by [campaignId]. */
    suspend fun saveCharacter(campaignId: String, character: PlayerCharacter) = withContext(Dispatchers.IO) {
        // Encoded polymorphically so the "type" discriminator is written and rows stay
        // readable if other Character subtypes are ever stored here.
        val payload = json.encodeToString<Character>(character)
        transaction(database) {
            // Upsert via delete + insert keyed on the composite primary key.
            CampaignCharacters.deleteWhere {
                (CampaignCharacters.ownerId eq character.ownerId) and
                    (CampaignCharacters.campaignId eq campaignId)
            }
            CampaignCharacters.insert {
                it[ownerId] = character.ownerId
                it[CampaignCharacters.campaignId] = campaignId
                it[data] = payload
            }
        }
    }

    /** Load every player character belonging to the campaign identified by [campaignId]. */
    suspend fun loadCharacters(campaignId: String): List<PlayerCharacter> = withContext(Dispatchers.IO) {
        transaction(database) {
            CampaignCharacters.selectAll()
                .where { CampaignCharacters.campaignId eq campaignId }
                .mapNotNull { rowToCharacter(it) }
        }
    }

    suspend fun deleteCharacter(campaignId: String, ownerId: String) = withContext(Dispatchers.IO) {
        transaction(database) {
            CampaignCharacters.deleteWhere {
                (CampaignCharacters.ownerId eq ownerId) and
                    (CampaignCharacters.campaignId eq campaignId)
            }
        }
    }

    private fun rowToCharacter(row: ResultRow): PlayerCharacter? =
        json.decodeFromString<Character>(row[CampaignCharacters.data]) as? PlayerCharacter
}
