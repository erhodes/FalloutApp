package com.erhodes.falloutapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("enemy")
class Enemy(
    override var name: String = "",
    override val ownerId: String = "",
    override val strength: Int = 1,
    override val perception: Int = 1,
    override val endurance: Int = 1,
    override val charisma: Int = 1,
    override val agility: Int = 1,
    override val intelligence: Int = 1,
    override val luck: Int = 1,
    override val skills: ArrayList<Int> = arrayListOf<Int>(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2),
    /** The [EnemyTemplate.id] this enemy was built from; used to rebuild its actions after loading. */
    val templateId: String = "",
) : Character() {

    val traits = HashSet<Trait>()
}
