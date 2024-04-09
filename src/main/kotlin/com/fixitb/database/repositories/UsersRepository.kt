package com.fixitb.database.repositories

import com.fixitb.database.DatabaseFactory.dbQuery
import com.fixitb.database.dao.UserDAO
import com.fixitb.models.User
import com.fixitb.models.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class UsersRepository: UserDAO{
    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        role = row[Users.role],
        email = row[Users.email],
        classId = row[Users.classId]


    )

    override suspend fun getUsers(): List<User> = dbQuery{
        Users.selectAll().map(::resultRowToUser)
    }
 // Insertar el usuario
    override suspend fun insertUser(email: String, classId: Int, role: String): User? = dbQuery{
        val insertStatement = Users.insert {
            it[Users.email] = email
            it[Users.classId] = classId
            it[Users.role] = role

        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)

    }

    override suspend fun getUserByEmail(email: String): User? = dbQuery{
        Users.select { Users.email eq email }.mapNotNull { resultRowToUser(it) }.singleOrNull()
    }
}