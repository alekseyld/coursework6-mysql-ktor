package com.alekseyld.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object MovieSessions : IntIdTable("movie_session") {
    val startDate = datetime("start_time")

    val cinemaHall = reference("cinema_hall_id", CinemaHalls)
    val movie = reference("movie_id", Movies)
    val sessionPrice = reference("session_price_id", Movies)
}

class MovieSession(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<MovieSession>(MovieSessions)

    var startDate by MovieSessions.startDate

    var cinemaHall by CinemaHall referencedOn MovieSessions.cinemaHall
    var movie by Movie referencedOn MovieSessions.movie
    var sessionPrice by SessionPrice referencedOn MovieSessions.sessionPrice
}