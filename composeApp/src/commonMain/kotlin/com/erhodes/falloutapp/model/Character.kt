package com.erhodes.falloutapp.model

import com.erhodes.falloutapp.util.AppLogger
import kotlinx.serialization.Serializable
import kotlin.math.max

@Serializable
class Character(
    val name: String,
    val strength: Int = 1,
    val perception: Int = 1,
    val endurance: Int = 1,
    val charisma: Int = 1,
    val agility: Int = 1,
    val intelligence: Int = 1,
    val luck: Int = 1,
    val skills: ArrayList<Int> = arrayListOf<Int>(2,2,2,2,2,2,2,2,2,2,2,2)
) {

    var level = 1
    var milestones = 0

    var damageTaken = 0
    var stress = 5
    var radiation = 0
    var fatigue = 0

    var load = 0
    val loadoutLimit = strength + 4
    var equippedArmor: Armor? = null


//    val transport = Ship(2, VehicleTemplate(1))
//
//    val wheels = Car(3, CarTemplate(2, false))

    var inventoryWeight = 0
    var inventoryLimit = 10

    val loadout = ArrayList<Item>()
    val inventory = ArrayList<Item>()

    val perks = HashSet<Perk>()

    fun gainMilestone() {
        milestones++
        if (milestones >= 3) {
            level++
            milestones = 0
        }
    }

    fun getMaxSkillValue(): Int {
        return 5 + level/2
    }

    fun gainPerk(perk: Perk) {
        if (perks.size > level) return
        perks.add(perk)
    }

    fun removePerk(perk: Perk) {
        perks.remove(perk)
    }

    fun addItemToInventory(item: Item) {
        inventory.add(item)
        inventoryWeight += item.load
    }

    fun removeItemFromInventory(item: Item) {
        inventory.remove(item)
        inventoryWeight -= item.load
    }

    /**
     * Equip an item from this character's inventory
     */
    fun equipItem(item: Item) {
        if (!inventory.contains(item)) return
        if (item is Armor) return equipArmor(item)
        if (item.load + load > loadoutLimit) return

        load += item.load
        loadout.add(item)
        removeItemFromInventory(item)
    }

    fun unequipItem(item: Item) {
        if (item == equippedArmor) unequipArmor(item as Armor)
        if (!loadout.contains(item)) return
        load -= item.load
        loadout.remove(item)
        addItemToInventory(item)
    }

    private fun equipArmor(armor: Armor) {
        AppLogger.d("Eric","equipping ${armor.name} current armor is ${equippedArmor != null}")
        if (equippedArmor != null) {
            AppLogger.d("Eric","nope")
            return
        }
        AppLogger.d("Eric","yes")
        load += (armor.load - 1)
        equippedArmor = armor
        removeItemFromInventory(armor)
    }

    private fun unequipArmor(armor: Armor) {
        load -= (armor.load - 1)
        equippedArmor = null
        addItemToInventory(armor)
    }

    fun getArmorDamage(): Int {
        return equippedArmor?.damageTaken ?: 0
    }

    fun getArmorDurability(): Int {
        return equippedArmor?.durability ?: 0
    }

    fun takeDamage(amount: Int) {
        if (amount <= 0 ) return
        var modifiedDamage = amount
        equippedArmor?.let { armor ->
            armor.damageTaken += max(0, amount - armor.toughness)
            if (armor.damageTaken > armor.durability) {
                modifiedDamage = armor.damageTaken - armor.durability
                armor.damageTaken = armor.durability
            }
        }
        damageTaken += modifiedDamage
    }

    fun healDamage(amount: Int) {
        damageTaken -= amount
        if (damageTaken < 0) damageTaken = 0
    }

    fun repairArmor(amount: Int) {
        equippedArmor?.let {
            it.damageTaken -= amount
            if (it.damageTaken < 0) it.damageTaken = 0
        }
    }
}