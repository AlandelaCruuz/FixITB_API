package com.fixitb.routes

import com.fixitb.database.repositories.UsersRepository
import com.fixitb.models.LoginResponse
import com.fixitb.models.Tokn
import com.fixitb.models.User
import com.fixitb.security.token.TokenClaim
import com.fixitb.security.token.TokenConfig
import com.fixitb.security.token.TokenService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.h2.command.Token

val usersRepository = UsersRepository()

fun Route.usersRouting(tokenService: TokenService, tokenConfig: TokenConfig){
    post("/login"){
        val newUser = call.receive<Tokn>()
        val verified = usersRepository.insertUser(newUser.idTokenn)
        if (verified != null) {
            val token = tokenService.generate(tokenConfig, TokenClaim("userEmail", "vdsav"))
            val response = LoginResponse(token.toString(), verified)
            call.respond(HttpStatusCode.OK, response)
        }
        else {
            call.respond(HttpStatusCode.NonAuthoritativeInformation, "Only ITB emails allowed")
        }
    }
    authenticate {
        route("/users"){
            get {
                val principal = call.principal<JWTPrincipal>()
                if (principal == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Token no proporcionado")
                    return@get
                }
                val users = usersRepository.getUsers()
                call.respond(users)
            }
            put("/{userId}/role/{newRole]") {
                val principal = call.principal<JWTPrincipal>()
                if (principal == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Token no proporcionado")
                    return@put
                }
                val userId = call.parameters["userId"]?.toIntOrNull()
                val newRole = call.parameters["newRole"]?.toString()
                if (userId == null || newRole == null) {
                    call.respondText("Invalid parameters", status = HttpStatusCode.BadRequest)
                    return@put
                }
                val updatedRole = usersRepository.assignRoleByUserId(userId, newRole)

                if (updatedRole != null) {
                    call.respond(updatedRole)
                } else {
                    call.respondText("User not found", status = HttpStatusCode.NotFound)
                }
            }

            get("{email}"){
                val principal = call.principal<JWTPrincipal>()
                if (principal == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Token no proporcionado")
                    return@get
                }
                val email = call.parameters["email"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing email parameter")
                val user = usersRepository.getUserByEmail(email)
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respond(HttpStatusCode.NotFound, "User with email $email not found")
                }
            }
            delete("/delete/{userId}"){
                val userId = call.parameters["userId"]?.toIntOrNull()
                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid user ID")
                    return@delete
                }
                val deleted = usersRepository.deleteUserById(userId)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "User deleted successfully")
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to delete user")
                }
            }
        }
    }
}