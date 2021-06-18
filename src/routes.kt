package com.plazzy

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.user() {
    get("/") {
        call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
    }

    get("/user") {
        call.respond("User")
    }

    get("/user/{id}") {
        val id = call.parameters["id"]
        call.respond("User id is $id")
    }

    post("/user") {
        call.respond(Person(1,"User"))
    }

    delete("/user/{id}") {
        val id = call.parameters["id"]
        call.respond("Deleted id is $id")
    }
}