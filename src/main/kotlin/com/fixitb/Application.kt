package com.fixitb

import com.fixitb.database.DatabaseFactory
import com.fixitb.plugins.*
import com.fixitb.security.token.TokenConfig
import com.fixitb.security.token.TokenImpl
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()

    val tokenService = TokenImpl()

    val secret = "secret"
    val issuer = "http://0.0.0.0:27031"
    val audience = "user"
    val myRealm = "myRealm"

    val tokenConfig = TokenConfig(
        issuer = issuer,
        audience = audience,
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = secret
    )
    configureSerialization()
    configureSecurity(tokenConfig)
    configureRouting(tokenService, tokenConfig)
}