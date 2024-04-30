package com.fixitb

import com.fixitb.database.DatabaseFactory
import com.fixitb.plugins.*
import com.fixitb.security.token.TokenConfig
import com.fixitb.security.token.TokenImpl
import io.imagekit.sdk.ImageKit
import io.imagekit.sdk.config.Configuration
import io.imagekit.sdk.models.FileCreateRequest
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.Base64


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

val imageKit = ImageKit.getInstance()
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
    val config = Configuration("public_lfFeom/UzdQDrhLRYAcJRMfTgfI=", "private_lc7HyJEGwju553Yhibt8G9PjNYg=", "https://ik.imagekit.io/fixitb")
    imageKit.setConfig(config)
    configureRouting(tokenService, tokenConfig)
}



fun saveImageToImageKit(bitmap: String, fileName: String): String {
    val response = imageKit.upload(
        FileCreateRequest(bitmap, fileName)
    )
    return response.url
}