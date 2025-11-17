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

    companion object {
        const val MAX_STRESS = 5
    }

    var level = 1
    var milestones = 0

    var damageTaken = 0
    var stress = 0
    var radiation = 0
    var fatigue = 0

    var loadoutWeight = 0
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

    fun getStatByOrdinal(ordinal: Int): Int {
        return when (ordinal) {
            0 -> strength
            1 -> perception
            2 -> endurance
            3 -> charisma
            4 -> intelligence
            5 -> agility
            6 -> luck
            else -> 0
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

    fun removeItem(item: Item) {
        if (item == equippedArmor) {
            loadoutWeight -= 1
            equippedArmor == null
        } else if (inventory.contains(item)) {
            removeItemFromInventory(item)
        } else if (loadout.contains(item)) {
            loadout.remove(item)
            loadoutWeight -= item.load
        }
    }



    /**
     * Equip an item from this character's inventory
     */
    fun equipItem(item: Item) {
        if (!inventory.contains(item)) return
        if (item is Armor) return equipArmor(item)
        if (item.load + loadoutWeight > loadoutLimit) return

        loadoutWeight += item.load
        loadout.add(item)
        removeItemFromInventory(item)
    }

    fun unequipItem(item: Item) {
        if (item == equippedArmor) unequipArmor(item as Armor)
        if (!loadout.contains(item)) return
        loadoutWeight -= item.load
        loadout.remove(item)
        addItemToInventory(item)
    }

    fun increaseStackCountForItem(item: Item, count: Int) {
        // if there was an ItemContainer class I could move the logic there, instead of managing these two arrays like this
        if (item !is StackableItem) return
        var inLoadout = false
        var limit: Int
        if (inventory.contains(item)) {
            limit = inventoryLimit
        } else if (loadout.contains(item)) {
            inLoadout = true
            limit = loadoutLimit
        } else {
            return
        }
        val oldLoad = item.load
        item.count += count
        val loadDif = item.load - oldLoad

        if (loadoutWeight + loadDif > limit) {
            // too heavy!
            item.count -= count
            return
        }

        if (inLoadout) {
            loadoutWeight += loadDif
        } else {
            inventoryWeight += loadDif
        }
    }

    fun decreaseStackCountForItem(item: Item, count: Int) {
        if (inventory.contains(item)) {
            (item as StackableItem).count-=count
            recalculateInventoryLoad()
        } else if (loadout.contains(item)) {
            (item as StackableItem).count-=count
            recalculateLoadoutLoad()
        }
    }

    private fun recalculateLoadoutLoad() {
        var weight = 0
        loadout.forEach {
            weight+=it.load
        }
        loadoutWeight = weight
    }

    private fun recalculateInventoryLoad() {
        var weight = 0
        inventory.forEach {
            weight+=it.load
        }
        inventoryWeight = weight
    }

    private fun equipArmor(armor: Armor) {
        AppLogger.d("Eric","equipping ${armor.name} current armor is ${equippedArmor != null}")
        if (equippedArmor != null) {
            return
        }
        loadoutWeight += 1
        equippedArmor = armor
        removeItemFromInventory(armor)
    }

    private fun unequipArmor(armor: Armor) {
        loadoutWeight -= 1
        equippedArmor = null
        addItemToInventory(armor)
    }

    fun getArmorDamage(): Int {
        return equippedArmor?.damageTaken ?: 0
    }

    fun getArmorDurability(): Int {
        return equippedArmor?.durability ?: 0
    }

    fun getArmorToughness(): Int {
        return equippedArmor?.toughness ?: 0
    }

    fun takeDamage(amount: Int) {
        if (amount <= 0 ) return
        var modifiedDamage = amount
        equippedArmor?.let { armor ->
            armor.damageTaken += max(0, modifiedDamage - armor.toughness)
            if (armor.damageTaken > armor.durability) {
                modifiedDamage = armor.damageTaken - armor.durability
                armor.damageTaken = armor.durability
            } else {
                modifiedDamage = 0
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

    fun modifyStress(amount: Int) {
        if (stress + amount !in 0..MAX_STRESS) return
        stress += amount
    }

    fun modifyFatigue(amount: Int) {
        if (fatigue + amount < getMinimumFatigue()) return
        fatigue += amount
    }

    fun getMinimumFatigue(): Int {
        return radiation/10
    }

    fun modifyRadiation(amount: Int) {
        if (radiation + amount < 0) return
        radiation += amount
        fatigue = max(fatigue, getMinimumFatigue())
    }

    fun qualifiesForPerk(perk: Perk): Boolean {
        perk.requirements.forEach { requirement ->
            if (!requirement.qualifiedForByCharacter(this)) return false
        }
        return true
    }
}