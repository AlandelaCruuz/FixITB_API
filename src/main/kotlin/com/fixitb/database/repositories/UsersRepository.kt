package com.fixitb.database.repositories

import com.fixitb.database.DatabaseFactory.dbQuery
import com.fixitb.database.dao.UserDAO
import com.fixitb.models.User
import com.fixitb.models.Users
import com.fixitb.security.token.TokenConfig
import com.fixitb.security.token.TokenService
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload
import java.util.*


class UsersRepository : UserDAO {

    private val transport = GoogleNetHttpTransport.newTrustedTransport()
    private val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance()
    private val CLIENT_ID = "115865438371-cf82nuu576brl30oqnh4qmk1d6gop95f.apps.googleusercontent.com"

    private val verifier = GoogleIdTokenVerifier.Builder(transport, jsonFactory)
        .setAudience(Collections.singletonList(CLIENT_ID))
        .build()


    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        role = row[Users.role],
        email = row[Users.email]

    )

    override suspend fun getUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }

    override suspend fun insertUser(paramToken: String): User? = dbQuery {
        val idTokenString = paramToken
        val idToken: GoogleIdToken? = verifier.verify(idTokenString)

        print(idToken)
        if (idToken != null) {
            val payload: Payload = idToken.payload
            // Obtener el email verificado desde el payload
            val verifiedEmail: String? = payload.email
            print("Email verificado: $verifiedEmail")
            // Comparar el email verificado con el email proporcionado
            // Insertar el usuario en la base de datos
            val existingUser = Users.select { Users.email eq payload.email }.singleOrNull()
            if (existingUser == null) {
                val insertStatement = Users.insert {
                    it[Users.email] = payload.email
                    it[Users.role] = "Student"
                }
                insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
            } else {
                resultRowToUser(existingUser)
            }

        } else {
            // El token es invÃ¡lido
            null
        }
    }

    override suspend fun getUserByEmail(email: String): User? = dbQuery {
        Users.select { Users.email eq email }.singleOrNull()?.let(::resultRowToUser)
    }

}