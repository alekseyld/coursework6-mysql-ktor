package com.alekseyld.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object SessionPrices : IntIdTable("session_price") {
    val name = varchar("name", 45)
    val lowPrice = integer("low_price")
    val mediumPrice = integer("medium_price")
    val highPrice = integer("high_price")
}

class SessionPrice(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SessionPrice>(SessionPrices)

    var name by SessionPrices.name
    var lowPrice by SessionPrices.lowPrice
    var mediumPrice by SessionPrices.mediumPrice
    var highPrice by SessionPrices.highPrice

    fun toModel() : SessionPriceModel {
        return SessionPriceModel(
            name = name,
            lowPrice = lowPrice,
            mediumPrice = mediumPrice,
            highPrice = highPrice,
        );
    }

}

data class SessionPriceModel(
    val name: String,
    val lowPrice: Int,
    val mediumPrice: Int,
    val highPrice: Int,
)