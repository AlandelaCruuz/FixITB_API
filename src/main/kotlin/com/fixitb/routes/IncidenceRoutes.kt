package com.fixitb.routes

import com.fixitb.database.repositories.IncidencesRepository
import com.fixitb.models.Incidence
import com.fixitb.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.text.SimpleDateFormat
import java.util.Date


val incidencesRepository = IncidencesRepository()

fun Route.incidencesRouting(){
    route("/incidences"){
        get {
            val incidences = incidencesRepository.getIncidences()
            call.respond(incidences)
        }
        post{
            val newIncidence = call.receive<Incidence>()
            incidencesRepository.insertIncidence(newIncidence.device, newIncidence.image, newIncidence.description, newIncidence.openDate, newIncidence.closeDate, newIncidence.status, newIncidence.classId, newIncidence.userAssigned, newIncidence.codeMain, newIncidence.codeMovistar)


            call.respond(HttpStatusCode.OK,newIncidence)
        }
    }
}
