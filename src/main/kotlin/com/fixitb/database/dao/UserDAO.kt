package com.fixitb.database.dao

import com.fixitb.models.Incidence
import com.fixitb.models.User

interface UserDAO {
    suspend fun getUsers(): List<User>
    suspend fun insertUser(email: String, role: String): User?
    suspend fun getUserByEmail(email: String): User?


}