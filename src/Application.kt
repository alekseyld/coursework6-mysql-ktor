package com.alekseyld

import com.alekseyld.database.DatabaseInit
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.thymeleaf.*
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

//            val date : Date = kotlin.runCatching {
//                LocalDate.parse(call.parameters["date"]).
//            }.getOrElse { Date() }




//            val json = transaction {
//
//                ExampleDataset.genresStrings.forEach {
//                    Genre.new {
//                        this.name = it
//                    }
//                }
//            }

//            call.respond(json)
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

