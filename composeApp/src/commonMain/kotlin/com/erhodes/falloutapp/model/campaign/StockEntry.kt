package com.erhodes.falloutapp.model.campaign

import com.erhodes.falloutapp.model.ItemTemplate
import kotlinx.serialization.Serializable

/**
 * One line of a [Store]'s stock: what it sells, how many are left, and what it charges in caps.
 *
 * Both [quantity] and [price] are mutable so a GM can restock or re-price without rebuilding the
 * entry. [template] needs no custom serializer here - [ItemTemplate] already serializes as its
 * catalog id and rehydrates from [com.erhodes.falloutapp.data.ItemDataSource].
 */
@Serializable
class StockEntry(
    val template: ItemTemplate,
    var quantity: Int,
    var price: Int,
)
