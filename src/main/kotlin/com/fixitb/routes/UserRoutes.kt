package com.fixitb.routes

import com.fixitb.database.repositories.UsersRepository
import com.fixitb.models.AuthResponse
import com.fixitb.models.Tokn
import com.fixitb.models.User
import com.fixitb.security.token.TokenClaim
import com.fixitb.security.token.TokenConfig
import com.fixitb.security.token.TokenService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val usersRepository = UsersRepository()

fun Route.usersRouting(tokenService: TokenService, tokenConfig: TokenConfig){
    route("/users"){
        get {
            val users = usersRepository.getUsers()
            call.respond(users)
        }
        post{
            val newUser = call.receive<Tokn>()
            println("TOKENN RECIBIDO PARAM")
            println(newUser.idTokenn)
            val verified = usersRepository.insertUser(newUser.idTokenn)
            if (verified != null) {
                val token = tokenService.generate(tokenConfig, TokenClaim("userEmail", "vdsav"))
                call.respond(HttpStatusCode.OK, AuthResponse(token))

            }
            else{
                call.respond(HttpStatusCode.NonAuthoritativeInformation, "Only ITB emails allowed")
            }


        }

        get("{email}"){
            val email = call.parameters["email"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing email parameter")
            val user = usersRepository.getUserByEmail(email)
            if (user != null) {
                call.respond(user)
            } else {
                call.respond(HttpStatusCode.NotFound, "User with email $email not found")
            }
        }
    }
}