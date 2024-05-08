package com.fixitb.database.repositories

import com.fixitb.database.DatabaseFactory.dbQuery
import com.fixitb.database.dao.UserDAO
import com.fixitb.models.Incidences
import com.fixitb.models.User
import com.fixitb.models.Users
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
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

    override suspend fun assignRoleByUserId(userId: Int, newRole: String): User? = dbQuery {
        Users.update ({ Users.id eq userId }){
            it[Users.role] = role
        }
        Users.select{ Users.id eq userId }.mapNotNull(::resultRowToUser).singleOrNull()
    }

    override suspend fun insertUser(paramToken: String): User? = dbQuery {
        val idTokenString = paramToken
        val idToken: GoogleIdToken? = verifier.verify(idTokenString)

        print(idToken)
        if (idToken != null) {
            val payload: GoogleIdToken.Payload = idToken.payload
            val verifiedEmail: String? = payload.email
            print("Email verificado: $verifiedEmail")
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
            null
        }
    }

    override suspend fun getUserByEmail(email: String): User? = dbQuery {
        Users.select { Users.email eq email }.singleOrNull()?.let(::resultRowToUser)
    }



    override suspend fun deleteUserById(userId: Int): Boolean = dbQuery{
        var success = false
        transaction {
            Incidences.deleteWhere { Incidences.userId eq userId }
            val deleteCount = Users.deleteWhere { Users.id eq userId }
            success = deleteCount > 0
        }
        success
    }

}

/**
 * override suspend fun insertUser(paramToken: String): User? = dbQuery {
 *         try {
 *             val decodedToken = FirebaseAuth.getInstance().verifyIdToken(paramToken)
 *             print(decodedToken)
 *             print(decodedToken.uid)
 *
 *             print("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa")
 *             val uid = decodedToken.uid
 *             val email = decodedToken.email
 *             println("Email verificado: $email")
 *
 *             // Verificar si el usuario ya existe en la base de datos
 *             val existingUser = Users.select { Users.email eq email }.singleOrNull()
 *             if (existingUser == null) {
 *                 // Si el usuario no existe, insertarlo en la base de datos
 *                 val insertStatement = Users.insert {
 *                     it[Users.email] = email
 *                     it[Users.role] = "Student"
 *                 }
 *                 insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
 *             } else {
 *                 // Si el usuario ya existe, devolverlo
 *                 resultRowToUser(existingUser)
 *             }
 *         } catch (e: FirebaseAuthException) {
 *             println("Error al verificar el token: ${e.message}")
 *             null
 *         }
 *     }
 */