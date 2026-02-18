package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.BasicItem
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.StackableItem
import com.erhodes.falloutapp.model.Weapon
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

//todo should this handle access to various kstore instances?
class DataManager {
    companion object {
        val serializerModule = SerializersModule {
            contextual(ItemTemplateSerializer)
            contextual(PerkSerializer)
            polymorphic(Item::class){
                subclass(BasicItem::class)
                subclass(Armor::class)
                subclass(StackableItem::class)
                subclass(Weapon::class)
            }
        }
    }
}