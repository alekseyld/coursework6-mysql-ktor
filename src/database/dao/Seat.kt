package com.alekseyld.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Seats : IntIdTable("seat") {
    val row = integer("row")
    val number = integer("number")

    val cinemaHall = reference("cinema_hall_id", CinemaHalls)
    val seatType = reference("seat_type_id", SeatTypes)
}

class Seat(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Seat>(Seats)

    var row by Seats.row
    var number by Seats.number

    var cinemaHall by CinemaHall referencedOn Seats.cinemaHall
    var seatType by SeatType referencedOn Seats.seatType

    fun toModel(): SeatModel {
        return SeatModel(
            id = this.id.value,
            row = this.row,
            number = this.number,
            seatType = seatType.name
        );
    }
}

data class SeatModel(
    val id: Int,
    val row: Int,
    val number: Int,
    val seatType: String
);