package com.alekseyld

import com.alekseyld.database.DatabaseInit
import com.alekseyld.database.ExampleDataset
import com.alekseyld.database.dao.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.thymeleaf.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.time.LocalDateTime

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/thymeleaf/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }

    install(Authentication) {
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowOrigin)
        anyHost()
        allowCredentials = true
        allowNonSimpleContentTypes = true
    }

//    install(DefaultHeaders) {
//        header(HttpHeaders.AccessControlAllowOrigin, "*")
//        header(HttpHeaders.AccessControlAllowCredentials, "true")
//        header(HttpHeaders.AccessControlAllowMethods, "GET, POST")
//    }

    DatabaseInit.connect()

    routing {


        get("/") {

//            MovieSession.new {
//                startDate = LocalDateTime.of(2021, 05, 16, )
//            }

//            ExampleDataset.generateCinemaHall("Зал №2")
//            ExampleDataset.createExampleMovieList()

//            call.respondRedirect("/movie_sessions")
            call.respondText("OK")
        }

        get("/createExampleDataSet") {
            ExampleDataset.createExampleDataSet()
            call.respondText("OK")
        }

        get("/movies") {

            val movies = transaction {
                Movie.all().limit(20).map { it.toModel() }
            }

            call.respond(movies)
        }

        get("/movie_sessions") {

            val movieId = call.parameters["movie_id"]!!.toInt();

            val response: List<MovieSessionModel> = transaction {

                val finded = MovieSession.find { MovieSessions.movie eq movieId }

                if (finded.empty()) {
                    mutableListOf()
                } else {
                    finded.map { it.toModel() }
                }

            }

            if (response.isEmpty()) {

                val movieSession = transaction {
                    MovieSession.new {
                        this.startDate = LocalDateTime.now().plusHours(1)

                        this.cinemaHall = CinemaHall[1]
                        this.movie = Movie[movieId]

                        this.sessionPrice = SessionPrice[1]
                    }.toModel()
                }

                (response as MutableList).add(movieSession)
            }

            call.respond(response)
        }

        get("/reservate_seat") {

            val seatId = call.parameters["seat_id"]!!.toInt();
            val movieSessionId = call.parameters["movie_session"]!!.toInt();

            val reservation = transaction {
                Reservation.new {
                    this.active = 1
                }
            }

            transaction {

                val seat = Seat[seatId]
                val movieSession = MovieSession[movieSessionId]

                SeatReserved.new {
                    this.seat = seat
                    this.movieSession = movieSession
                    this.reservation = reservation
                }
            }

            call.respond(mapOf("response" to "OK"))
        }

        get("/html-thymeleaf") {
            call.respond(ThymeleafContent("index", mapOf("user" to ThymeleafUser(1, "user1"))))
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}

data class ThymeleafUser(val id: Int, val name: String)

