package com.erhodes.falloutapp

import com.erhodes.falloutapp.data.DataManager
import com.erhodes.falloutapp.data.EnemyDataSource
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.Enemy
import com.erhodes.falloutapp.model.Weapon
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class EnemyTemplateTests {
    private val json: Json = Json {
        serializersModule = DataManager.serializerModule
        ignoreUnknownKeys = true
        classDiscriminator = "type"
    }

    private fun Enemy.weaponTemplateIds(): List<Int> =
        (loadout + inventory).filterIsInstance<Weapon>().map { it.template.id }.sorted()

    @Test
    fun templateIdsAreUnique() {
        val ids = EnemyDataSource.all.map { it.id }
        assertEquals(ids.size, ids.toSet().size, "Duplicate enemy template id in $ids")
    }

    /** equipItem fails silently when over the loadout limit, so check every template's gear fits. */
    @Test
    fun createEquipsAllGear() {
        EnemyDataSource.all.forEach { template ->
            val enemy = template.create()
            assertEquals(template.id, enemy.templateId)
            assertEquals(
                template.weapons.size + template.stacks.size,
                enemy.loadout.size,
                "${template.name}: not all weapons/stacks fit in the loadout (limit ${enemy.loadoutLimit})"
            )
            if (template.armorId != null) {
                assertNotNull(enemy.equippedArmor, "${template.name}: armor was not equipped")
            }
            assertEquals(template.traitIds.size, enemy.traits.size, template.name)
        }
    }

    @Test
    fun createBuildsOneActionPerAttack() {
        EnemyDataSource.all.forEach { template ->
            val enemy = template.create()
            assertEquals(template.weapons.sumOf { it.attacks.size }, enemy.actions.size, template.name)
        }
    }

    /** Actions are transient and enemy-only items must not be confused with player items. */
    @Test
    fun encounterRoundTripRestoresWeaponsAndActions() {
        val encounter = Encounter("Round trip")
        val originals = EnemyDataSource.all.map { it.create() }
        originals.forEach { encounter.addCharacter(it) }

        val decoded = json.decodeFromString<Encounter>(json.encodeToString(encounter))
        EnemyDataSource.restore(decoded)

        originals.zip(decoded.characters).forEach { (original, copy) ->
            assertIs<Enemy>(copy)
            assertEquals(original.weaponTemplateIds(), copy.weaponTemplateIds(), original.name)
            assertEquals(original.actions.map { it.title }, copy.actions.map { it.title }, original.name)
            assertEquals(original.actions.size, copy.actions.size, original.name)
        }
    }
}
