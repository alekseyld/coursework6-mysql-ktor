package com.alekseyld.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Reservations : IntIdTable("reservation") {
    val active = integer("active")
}

class Reservation(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Reservation>(Reservations)

    var active by Reservations.active
}