package com.alekseyld.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object SessionPrices : IntIdTable("session_price") {
    val name: Column<String> = varchar("name", 45)
}

class SessionPrice(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SessionPrice>(SessionPrices)
    var name by SessionPrices.name
}