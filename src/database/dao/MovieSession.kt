package com.alekseyld.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.format.DateTimeFormatter

object MovieSessions : IntIdTable("movie_session") {
    val startDate = datetime("start_time")

    val cinemaHall = reference("cinema_hall_id", CinemaHalls)
    val movie = reference("movie_id", Movies)
    val sessionPrice = reference("session_price_id", SessionPrices)
}

class MovieSession(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<MovieSession>(MovieSessions)

    var startDate by MovieSessions.startDate

    var cinemaHall by CinemaHall referencedOn MovieSessions.cinemaHall
    var movie by Movie referencedOn MovieSessions.movie
    var sessionPrice by SessionPrice referencedOn MovieSessions.sessionPrice

    fun toModel(): MovieSessionModel {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        val seats = Seat.find { Seats.cinemaHall eq cinemaHall.id }
        val seatReserved = SeatReserved.find { SeatReserveds.movieSession eq id }

        return MovieSessionModel(
            id = id.value,
            startDate = startDate.format(formatter),
            cinemaHall = cinemaHall.toModel(),
            sessionPrice = sessionPrice.toModel(),
            seats = seats.map { it.toModel() },
            seatsReserved = seatReserved.map { it.toModel() }
        );
    }
}

data class MovieSessionModel(
    val id: Int,
    val startDate: String,
    val cinemaHall: CinemaHallModel,
    val sessionPrice: SessionPriceModel,
    val seats: List<SeatModel>,
    val seatsReserved: List<SeatReservedModel>,
)