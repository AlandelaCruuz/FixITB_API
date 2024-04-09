package com.fixitb.routes

import com.fixitb.database.repositories.IncidencesRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


val incidencesRepository = IncidencesRepository()

fun Route.incidencesRouting(){
    route("/incidences"){
        get {
            val incidences = incidencesRepository.getIncidences()
            call.respond(incidences)
        }
    }
}