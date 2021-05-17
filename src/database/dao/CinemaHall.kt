package com.alekseyld.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object CinemaHalls : IntIdTable("cinema_hall") {
    val name: Column<String> = varchar("name", 45)
}

class CinemaHall(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CinemaHall>(CinemaHalls)

    var name by CinemaHalls.name

    fun toModel() : CinemaHallModel {
        return CinemaHallModel(
            id = this.id.value,
            name = this.name
        );
    }
}

data class CinemaHallModel(
    val id: Int,
    val name: String
)

