package com.fixitb.plugins

import com.fixitb.routes.incidencesRouting
import com.fixitb.routes.usersRouting
import com.fixitb.security.token.TokenConfig
import com.fixitb.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(tokenService: TokenService, tokenConfig: TokenConfig) {
    routing {
        usersRouting(tokenService, tokenConfig)
        incidencesRouting()
    }
}
