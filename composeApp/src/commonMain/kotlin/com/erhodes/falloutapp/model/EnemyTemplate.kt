package com.erhodes.falloutapp.model

import com.erhodes.falloutapp.data.EnemyItemDataSource
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.data.TraitDataSource
import com.erhodes.falloutapp.model.action.MeleeAttack
import com.erhodes.falloutapp.model.action.RangedAttack

enum class AttackType { RANGED, MELEE }

/**
 * A weapon an enemy carries. [templateId] is looked up in [ItemDataSource] or, failing that,
 * [EnemyItemDataSource]. [attacks] lists the attack actions the enemy gets with it; leave it empty
 * for a weapon that is carried but not given its own action.
 */
data class EnemyWeapon(val templateId: Int, val attacks: List<AttackType>)

fun ranged(templateId: Int) = EnemyWeapon(templateId, listOf(AttackType.RANGED))
fun melee(templateId: Int) = EnemyWeapon(templateId, listOf(AttackType.MELEE))
fun rangedAndMelee(templateId: Int) = EnemyWeapon(templateId, listOf(AttackType.RANGED, AttackType.MELEE))
fun carried(templateId: Int) = EnemyWeapon(templateId, emptyList())

/** A stackable item (e.g. ammo) an enemy carries equipped. */
data class EnemyStack(val templateId: Int, val count: Int)

/**
 * Blueprint for an enemy type. [create] builds a fresh [Enemy] instance; the template is the
 * single place that knows how to equip one and give it actions.
 */
data class EnemyTemplate(
    val id: String,
    val name: String,
    val strength: Int,
    val perception: Int,
    val endurance: Int,
    val charisma: Int,
    val intelligence: Int,
    val agility: Int,
    val luck: Int,
    /** Overrides on top of the default skill value of 2. */
    val skills: Map<Skills, Int> = emptyMap(),
    val health: Int = Character.BASE_MAX_HEALTH,
    val weapons: List<EnemyWeapon> = emptyList(),
    val armorId: Int? = null,
    val stacks: List<EnemyStack> = emptyList(),
    val traitIds: List<Int> = emptyList(),
) {
    fun create(): Enemy {
        val enemy = Enemy(
            name = name,
            strength = strength,
            perception = perception,
            endurance = endurance,
            charisma = charisma,
            intelligence = intelligence,
            agility = agility,
            luck = luck,
            templateId = id,
        )
        skills.forEach { (skill, value) -> enemy.skills[skill.ordinal] = value }
        enemy.health = health

        weapons.forEach { equip(enemy, Weapon(lookupItem(it.templateId), 1)) }
        armorId?.let { equip(enemy, Armor(lookupItem(it), 0)) }
        stacks.forEach { equip(enemy, StackableItem(lookupItem(it.templateId), it.count)) }
        traitIds.forEach { enemy.traits.add(TraitDataSource.getTraitById(it)) }

        buildActions(enemy)
        return enemy
    }

    /**
     * Recreates [enemy]'s actions from this template. Actions aren't serialized, so this has to run
     * again whenever an enemy is deserialized.
     */
    fun buildActions(enemy: Enemy) {
        enemy.actions.clear()
        val carriedWeapons = (enemy.loadout + enemy.inventory).filterIsInstance<Weapon>()
        weapons.forEach { spec ->
            val weapon = carriedWeapons.find { it.template.id == spec.templateId } ?: return@forEach
            spec.attacks.forEach { type ->
                enemy.actions.add(
                    when (type) {
                        AttackType.RANGED -> RangedAttack(weapon)
                        AttackType.MELEE -> MeleeAttack(weapon)
                    }
                )
            }
        }
    }

    private fun equip(enemy: Enemy, item: Item) {
        enemy.addItemToInventory(item)
        enemy.equipItem(item)
    }

    private fun lookupItem(templateId: Int): ItemTemplate =
        ItemDataSource.itemMap[templateId] ?: EnemyItemDataSource.getItemTemplateById(templateId)
}
