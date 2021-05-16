package com.alekseyld.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object SeatReserveds : IntIdTable("seat_reserved") {
    val movieSession = reference("movie_session_id", MovieSessions)
    val reservation = reference("reservation_id", Reservations)
    val seat = reference("seat_id", Seats)
}

class SeatReserved(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SeatReserved>(SeatReserveds)

    var movieSession by MovieSession referencedOn SeatReserveds.movieSession
    var reservation by Reservation referencedOn SeatReserveds.reservation
    var seat by Seat referencedOn SeatReserveds.seat
}