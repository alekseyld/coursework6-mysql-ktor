package com.alekseyld.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object SeatTypes : IntIdTable("seat_type") {
    val name: Column<String> = varchar("name", 45)
}

class SeatType(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SeatType>(SeatTypes)
    var name by SeatTypes.name
}