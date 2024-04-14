package com.fixitb.routes

import com.fixitb.database.repositories.UsersRepository
import com.fixitb.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val usersRepository = UsersRepository()

fun Route.usersRouting(){
    route("/users"){
        get {
            val users = usersRepository.getUsers()
            call.respond(users)
        }
        post {
            val newUser = call.receive<User>()
            usersRepository.insertUser(newUser.email, newUser.classId, newUser.role)
            call.respond(HttpStatusCode.OK, newUser)
        }

    }
}