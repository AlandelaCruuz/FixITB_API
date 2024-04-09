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
        post{
            val newUser = call.receive<User>()
            val existingUser = usersRepository.getUserByEmail(newUser.email)
            if (existingUser == null){
                call.respond(HttpStatusCode.Found, "new_user")
            } else{
                usersRepository.insertUser(
                    email = newUser.email,
                    classId = newUser.classId,
                    role = newUser.role
                )
                call.respondText("Ha iniciat sessió correctament")
            }

        }
    }
}