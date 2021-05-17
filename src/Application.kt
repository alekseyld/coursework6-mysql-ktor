package com.alekseyld

import com.alekseyld.database.DatabaseInit
import com.alekseyld.database.ExampleDataset
import com.alekseyld.database.dao.MovieSession
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.thymeleaf.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

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

    DatabaseInit.connect()

    routing {
        get("/") {

//            MovieSession.new {
//                startDate = LocalDateTime.of(2021, 05, 16, )
//            }

//            ExampleDataset.generateCinemaHall("Зал №2")
            ExampleDataset.createExampleMovieList()

//            call.respondRedirect("/movie_sessions")
        }

        get("/movie_sessions") {

            val movieSessions : List<MovieSession> = transaction {
                MovieSession.all().toList()
            }

            call.respond(
                ThymeleafContent(
                    "movie_sessions",
                    mapOf(
                        "movieSessions" to movieSessions
                    )
                )
            )
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

