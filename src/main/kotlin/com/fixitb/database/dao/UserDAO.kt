package com.fixitb.database.dao

import com.fixitb.models.Incidence
import com.fixitb.models.User

interface UserDAO {
    suspend fun getUsers(): List<User>
    suspend fun insertUser(paramToken: String): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun assignRoleByUserId(userId: Int, newRole: String): User?
    suspend fun deleteUserById(userId: Int): Boolean

}