package com.fixitb.routes

import com.fixitb.database.repositories.IncidencesRepository
import com.fixitb.models.Incidence
import com.fixitb.models.User
import com.fixitb.saveImageToImageKit
import com.fixitb.security.token.TokenConfig
import com.fixitb.security.token.TokenService
import io.imagekit.sdk.ImageKit
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.text.SimpleDateFormat

import java.util.Date

val incidencesRepository = IncidencesRepository()

fun Route.incidencesRouting() {
    authenticate {
        route("/incidences") {
            get {
                val incidences = incidencesRepository.getIncidences()
                call.respond(incidences)
            }
            post {
                val newIncidence = call.receive<Incidence>()
                val imageUrl = newIncidence.image?.let {
                    saveImageToImageKit(it, "Incidencia_.jpg")
                }
                incidencesRepository.insertIncidence(
                    newIncidence.device,
                    imageUrl?:"",
                    newIncidence.description,
                    newIncidence.openDate,
                    newIncidence.closeDate?:"",
                    newIncidence.status,
                    newIncidence.classNum,
                    newIncidence.userAssigned?:"",
                    newIncidence.codeMain?:"",
                    newIncidence.codeMovistar?:0,
                    newIncidence.userId,
                    newIncidence.title
                )
                call.respond(HttpStatusCode.OK, newIncidence)
            }
            get("{userId}") {
                if (call.parameters["userId"].isNullOrBlank()) {
                    return@get call.respondText("Missing ID", status = HttpStatusCode.BadRequest)
                }
                val id = call.parameters["userId"]
                val incidencesByUserId = incidencesRepository.getIncidencesByUserId(id!!.toInt())
                call.respond(incidencesByUserId)
            }

            put("{incidenceId}") {
                val incidenceId = call.parameters["incidenceId"]?.toIntOrNull()
                if (incidenceId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid incidence ID")
                    return@put
                }

                val incidence = call.receive<Incidence>()

                val updated = incidencesRepository.updateIncidenceById(
                    incidenceId,
                    incidence.device,
                    incidence.image?:"",
                    incidence.description,
                    incidence.status,
                    incidence.classNum,
                    incidence.userAssigned?:"",
                    incidence.codeMain?:"",
                    incidence.codeMovistar?:0,
                    incidence.title,
                    incidence.closeDate?:""

                )

                if (updated) {
                    call.respond(HttpStatusCode.OK, "Incidence updated successfully")
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to update incidence")
                }
            }
            delete("/delete/{incidenceId}"){
                val incidenceId = call.parameters["incidenceId"]?.toIntOrNull()
                if (incidenceId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid incidence ID")
                    return@delete
                }
                val deleted = incidencesRepository.deleteIncidenceById(incidenceId)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Incidence deleted successfully")
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to delete incidence")
                }
            }

        }

    }
}

