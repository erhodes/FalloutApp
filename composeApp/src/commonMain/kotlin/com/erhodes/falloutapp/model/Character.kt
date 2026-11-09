package com.erhodes.falloutapp.model

import com.erhodes.falloutapp.model.action.Action
import com.erhodes.falloutapp.model.condition.Condition
import com.erhodes.falloutapp.model.condition.ConditionTemplate
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.math.max
import kotlin.math.min
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
sealed class Character {

    /**
     * Stable identity for this character, following the same convention as [Encounter],
     * `Location` and `Feature`.
     *
     * It lives on the base class, not on the subclasses, because the lists that most need to
     * address one element are typed against [Character] - `Encounter.characters` holds both
     * [PlayerCharacter]s and [Enemy]s, and addresses them by list position today.
     *
     * `ownerId` can't serve this purpose: it identifies the *player*, not the character, it is empty
     * until a login completes, and [Enemy] never sets it at all - so every enemy shares the same
     * blank one, and two of the same enemy type are otherwise identical in every field.
     *
     * Declared in the body rather than the constructor so neither subclass has to thread it through;
     * kotlinx-serialization still writes and restores it, via the synthetic constructor it generates
     * for deserialization.
     */
    val id: String = Uuid.random().toString()

    abstract var name: String
    abstract val ownerId: String
    abstract val strength: Int
    abstract val perception: Int
    abstract val endurance: Int
    abstract val charisma: Int
    abstract val agility: Int
    abstract val intelligence: Int
    abstract val luck: Int
    abstract val skills: ArrayList<Int>

    companion object {
        const val MAX_HEALTH = 5
    }

    var damageTaken = 0
    var radiation = 0
    var fatigue = 0
    var fear = 0

    var loadoutWeight = 0
    val loadoutLimit get() = strength + 4
    var equippedArmor: Armor? = null

    var speed: Int = 6

    var inventoryWeight = 0
    val inventoryLimit get() = strength + 15

    val loadout = ArrayList<Item>()
    val inventory = ArrayList<Item>()

    val perks = HashSet<Perk>()

    val conditions: HashSet<Condition> = hashSetOf()

    @Transient
    val actions: ArrayList<Action> = arrayListOf()

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
            loadoutWeight -= (item as Armor).getEquippedWeight()
            equippedArmor = null
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
        if (item is Weapon) {
            //todo obviously this is super hacky and needs to be refactored
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
            item.ammo += count
            val loadDif = item.load - oldLoad

            if (loadoutWeight + loadDif > limit) {
                // too heavy!
                item.ammo -= count
                return
            }

            if (inLoadout) {
                loadoutWeight += loadDif
            } else {
                inventoryWeight += loadDif
            }
        }
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
        //todo refactor this
        if (item is Weapon && item.ammo - count >= 0) {
            if (inventory.contains(item)) {
                item.ammo-=count
                recalculateInventoryWeight()
            } else if (loadout.contains(item)) {
                item.ammo-=count
                recalculateLoadoutWeight()
            }
        }
        if (item is StackableItem && item.count - count >= 0) {
            if (inventory.contains(item)) {
                item.count-=count
                recalculateInventoryWeight()
            } else if (loadout.contains(item)) {
                item.count-=count
                recalculateLoadoutWeight()
            }
        }
    }

    /**
     * Total count of [templateId] held across inventory and loadout. A character can hold several
     * stacks of the same template, so the quantity of anything spendable is a sum, never one stack.
     */
    fun countStackable(templateId: Int): Int =
        (inventory + loadout)
            .filterIsInstance<StackableItem>()
            .filter { it.template.id == templateId }
            .sumOf { it.count }

    /**
     * Drains [amount] of [templateId] across however many stacks it takes. Returns false and
     * changes nothing when the character is short, so a caller can treat it as the whole
     * transaction rather than checking first and hoping nothing moved in between.
     */
    fun consumeStackable(templateId: Int, amount: Int): Boolean {
        if (countStackable(templateId) < amount) return false
        var remaining = amount
        // Snapshot before draining: decreaseStackCountForItem mutates the stacks we're walking.
        val stacks = (inventory + loadout)
            .filterIsInstance<StackableItem>()
            .filter { it.template.id == templateId }
        for (stack in stacks) {
            if (remaining <= 0) break
            val take = min(stack.count, remaining)
            decreaseStackCountForItem(stack, take)
            remaining -= take
        }
        return true
    }

    private fun recalculateLoadoutWeight() {
        var weight = 0
        loadout.forEach {
            weight+=it.load
        }
        equippedArmor?.let { weight+=it.getEquippedWeight() }
        loadoutWeight = weight
    }

    private fun recalculateInventoryWeight() {
        var weight = 0
        inventory.forEach {
            weight+=it.load
        }
        inventoryWeight = weight
    }

    private fun equipArmor(armor: Armor) {
//        AppLogger.d("Eric","equipping ${armor.name} current armor is ${equippedArmor != null}")
        if (equippedArmor != null) {
            return
        }
        loadoutWeight += armor.getEquippedWeight()
        equippedArmor = armor
        removeItemFromInventory(armor)
    }

    private fun unequipArmor(armor: Armor) {
        loadoutWeight -= armor.getEquippedWeight()
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
        if (isBloodied()) {
            addCondition(Condition.buildNewCondition(ConditionTemplate.Bloodied))
        }
    }

    fun healDamage(amount: Int) {
        damageTaken -= amount
        if (damageTaken < 0) damageTaken = 0
        if (!isBloodied()) {
            removeCondition(ConditionTemplate.Bloodied)
        }
    }

    fun repairArmor(amount: Int) {
        equippedArmor?.let {
            it.damageTaken -= amount
            if (it.damageTaken < 0) it.damageTaken = 0
        }
    }

    fun isBloodied(): Boolean {
        return damageTaken >=endurance
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

    fun modifyFear(amount: Int) {
        if (fear + amount < 0) return
        fear += amount
        when (fear) {
            in 0..<getCourage() -> {
                removeCondition(ConditionTemplate.Shaken)
                removeCondition(ConditionTemplate.Panicked)
                removeCondition(ConditionTemplate.Broken)
            }
            in getCourage()..<getCourage() * 2 -> {
                addCondition(Condition.buildNewCondition(ConditionTemplate.Shaken))
                removeCondition(ConditionTemplate.Panicked)
                removeCondition(ConditionTemplate.Broken)
            }
            in getCourage() * 2..<getCourage() * 3 -> {
                removeCondition(ConditionTemplate.Shaken)
                addCondition(Condition.buildNewCondition(ConditionTemplate.Panicked))
                removeCondition(ConditionTemplate.Broken)
            }
            else -> {
                removeCondition(ConditionTemplate.Shaken)
                removeCondition(ConditionTemplate.Panicked)
                addCondition(Condition.buildNewCondition(ConditionTemplate.Broken))
            }
        }
    }

    fun getCourage(): Int {
        return intelligence + getArmorToughness()
    }

    fun getFearLevel(): FearLevel {
        return when {
            fear in 0..< getCourage() -> FearLevel.STEADY
            fear in getCourage()..< getCourage() * 2 -> FearLevel.SHAKEN
            fear in getCourage() * 2..< getCourage() * 3 -> FearLevel.PANICKED
            else -> FearLevel.BROKEN
        }
    }

    fun addCondition(condition: Condition) {
        if (conditions.find { c -> c.template == condition.template } != null) {
            return
        }
        conditions.add(condition)
    }

    fun removeCondition(condition: Condition) {
        conditions.remove(condition)
    }

    fun removeCondition(condition: ConditionTemplate) {
        conditions.removeAll { it.template == condition }
    }
}
