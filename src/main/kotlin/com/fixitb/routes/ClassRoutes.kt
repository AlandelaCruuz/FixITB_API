package com.fixitb.routes

import com.fixitb.database.repositories.ClassesRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val classesRepository = ClassesRepository()

fun Route.classesRouting(){
    route("/classes"){
        get{
            val classes = classesRepository.getClasses()
            call.respond(classes)
        }
    }
}