package com.fixitb.plugins

import com.fixitb.routes.incidencesRouting
import com.fixitb.routes.usersRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        usersRouting()
        incidencesRouting()
    }
}
