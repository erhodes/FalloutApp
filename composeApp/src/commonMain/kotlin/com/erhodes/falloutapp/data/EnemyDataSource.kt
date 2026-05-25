package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.data.ItemDataSource.ID_ARMOR_LEATHER
import com.erhodes.falloutapp.data.ItemDataSource.ID_MINIGUN
import com.erhodes.falloutapp.data.ItemDataSource.ID_PA_RAIDER
import com.erhodes.falloutapp.data.ItemDataSource.ID_SNIPER_RIFLE
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.Enemy
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.model.action.MeleeAttack
import com.erhodes.falloutapp.model.action.RangedAttack

object EnemyDataSource {

    fun createRaiderShotgunner(): Enemy {
        val raiderShotgunner = Enemy(
            name = "Raider Shotgunner",
            strength = 2,
            perception = 2,
            endurance = 2,
            charisma = 2,
            intelligence = 2,
            agility = 2,
            luck = 2
        )
        raiderShotgunner.skills[Skills.GUNS.ordinal] = 5
        val shotgun = Weapon(ItemDataSource.getItemTemplateById(6), 1)
        raiderShotgunner.addItemToInventory(shotgun)
        raiderShotgunner.equipItem(shotgun)
        val armor = Armor(ItemDataSource.getItemTemplateById(ID_ARMOR_LEATHER), 0)
        raiderShotgunner.addItemToInventory(armor)
        raiderShotgunner.equipItem(armor)
        raiderShotgunner.actions.add(RangedAttack(shotgun))
        return raiderShotgunner
    }

    fun createRaiderPsycho(): Enemy {
        val raiderPsycho = Enemy(
            name = "Raider Psycho",
            strength = 3,
            perception = 2,
            endurance = 2,
            charisma = 2,
            intelligence = 2,
            agility = 2,
            luck = 2
        )
        raiderPsycho.skills[Skills.MELEE.ordinal] = 5
        val sledgehammer = Weapon(ItemDataSource.getItemTemplateById(7), 1)
        raiderPsycho.addItemToInventory(sledgehammer)
        raiderPsycho.equipItem(sledgehammer)
        val armor = Armor(ItemDataSource.getItemTemplateById(ID_ARMOR_LEATHER), 0)
        raiderPsycho.addItemToInventory(armor)
        raiderPsycho.equipItem(armor)
        raiderPsycho.actions.add(MeleeAttack(sledgehammer))
        return raiderPsycho
    }

    fun createRaiderLieutenant(): Enemy {
        val raiderLieutenant = Enemy(
            name = "Raider Lieutenant",
            strength = 1,
            perception = 2,
            endurance = 2,
            charisma = 3,
            intelligence = 2,
            agility = 3,
            luck = 1,
        )
        raiderLieutenant.skills[Skills.GUNS.ordinal] = 5
        raiderLieutenant.skills[Skills.SPEECH.ordinal] = 5
        val assaultRifle = Weapon(ItemDataSource.getItemTemplateById(ItemDataSource.ID_ASSAULT_RIFLE), 1)
        raiderLieutenant.addItemToInventory(assaultRifle)
        raiderLieutenant.equipItem(assaultRifle)
        val armor = Armor(ItemDataSource.getItemTemplateById(ID_ARMOR_LEATHER), 0)
        raiderLieutenant.addItemToInventory(armor)
        raiderLieutenant.equipItem(armor)
        raiderLieutenant.actions.add(RangedAttack(assaultRifle))
        raiderLieutenant.traits.add(TraitDataSource.getTraitById(1))
        return raiderLieutenant
    }

    fun createGhoul(): Enemy {
        val ghoul = Enemy(
            name = "Ghoul",
            strength = 3,
            perception = 2,
            endurance = 3,
            charisma = 1,
            intelligence = 1,
            agility = 2,
            luck = 2,
        )
        ghoul.skills[Skills.MELEE.ordinal] = 5
        val radioactiveFists = Weapon(EnemyItemDataSource.getItemTemplateById(1), 1)
        ghoul.addItemToInventory(radioactiveFists)
        ghoul.equipItem(radioactiveFists)
        ghoul.actions.add(MeleeAttack(radioactiveFists))
        ghoul.traits.add(TraitDataSource.getTraitById(3))
        ghoul.traits.add(TraitDataSource.getTraitById(4))
        return ghoul
    }

    fun createProtectron(): Enemy {
        val protectron = Enemy(
            name = "Protectron",
            strength = 2,
            perception = 2,
            endurance = 3,
            charisma = 1,
            intelligence = 3,
            agility = 2,
            luck = 1,
        )
        protectron.skills[Skills.GUNS.ordinal] = 8
        protectron.skills[Skills.MELEE.ordinal] = 6
        val laserPistol = Weapon(ItemDataSource.getItemTemplateById(12), 1)
        protectron.addItemToInventory(laserPistol)
        protectron.equipItem(laserPistol)
        val armor = Armor(ItemDataSource.getItemTemplateById(1002), 0)
        protectron.addItemToInventory(armor)
        protectron.equipItem(armor)
        protectron.actions.add(RangedAttack(laserPistol))
        protectron.actions.add(MeleeAttack(laserPistol))
        protectron.traits.add(TraitDataSource.getTraitById(5))
        return protectron
    }

    fun createRaiderHeavy(): Enemy {
        val raiderHeavy = Enemy(
            name = "Raider Heavy",
            strength = 3,
            perception = 2,
            endurance = 3,
            charisma = 1,
            intelligence = 1,
            agility = 3,
            luck = 1,
        )
        raiderHeavy.skills[Skills.GUNS.ordinal] = 6
        val minigun = Weapon(ItemDataSource.getItemTemplateById(ID_MINIGUN), 1)
        raiderHeavy.addItemToInventory(minigun)
        raiderHeavy.equipItem(minigun)
        val armor = Armor(ItemDataSource.getItemTemplateById(ID_PA_RAIDER), 0)
        raiderHeavy.addItemToInventory(armor)
        raiderHeavy.equipItem(armor)
        raiderHeavy.actions.add(RangedAttack(minigun))
        return raiderHeavy
    }

    fun createRaiderStalker(): Enemy {
        val raiderStalker = Enemy(
            name = "Raider Stalker",
            strength = 2,
            perception = 3,
            endurance = 2,
            charisma = 1,
            intelligence = 2,
            agility = 3,
            luck = 1,
        )
        raiderStalker.skills[Skills.GUNS.ordinal] = 6
        raiderStalker.skills[Skills.SNEAK.ordinal] = 6
        val sniperRifle = Weapon(ItemDataSource.getItemTemplateById(ID_SNIPER_RIFLE), 1)
        raiderStalker.addItemToInventory(sniperRifle)
        raiderStalker.equipItem(sniperRifle)
        val armor = Armor(ItemDataSource.getItemTemplateById(ID_ARMOR_LEATHER), 0)
        raiderStalker.addItemToInventory(armor)
        raiderStalker.equipItem(armor)
        raiderStalker.actions.add(RangedAttack(sniperRifle))
        raiderStalker.traits.add(TraitDataSource.getTraitById(9))
        raiderStalker.traits.add(TraitDataSource.getTraitById(8))
        return raiderStalker
    }

    fun createZapper(): Enemy {
        val zapper = Enemy(
            name = "Zapper",
            strength = 2,
            perception = 2,
            endurance = 3,
            charisma = 1,
            intelligence = 1,
            agility = 3,
            luck = 2,
        )
        zapper.skills[Skills.GUNS.ordinal] = 5
        zapper.skills[Skills.ATHLETICS.ordinal] = 5
        val lightning = Weapon(EnemyItemDataSource.getItemTemplateById(0), 1)
        zapper.addItemToInventory(lightning)
        zapper.equipItem(lightning)
        zapper.actions.add(RangedAttack(lightning))
        zapper.traits.add(TraitDataSource.getTraitById(2))
        return zapper
    }
}
