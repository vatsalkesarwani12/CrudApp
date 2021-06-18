package com.plazzy

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

data class User(val id: Int, val name: String, val age: Int)

val users = mutableListOf<User>()

//fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}

fun Application.configureRouting() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        user()
        get("/users") {
            call.respond(users)
        }
        post("/user") {
            val requestBody = call.receive<User>()
            val user = requestBody.copy(id = users.size.plus(1))
            users.add(user)
            call.respond(user)
        }

        get("/user") {
            val id = call.request.queryParameters["id"]
            val user = users.find { it.id == id?.toInt() }
            val response = user ?: "User not found"
            call.respond(response)
        }

        delete("/user") {
            val id = call.request.queryParameters["id"]
            users.removeIf {
                it.id == id?.toInt()
            }
            call.respond("User deleted")
        }
    }
}
