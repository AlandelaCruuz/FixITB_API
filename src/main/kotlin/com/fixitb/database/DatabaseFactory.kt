package com.fixitb.database

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        val user = "avnadmin"
        val password = "AVNS_pGDnqLw3W1JnSBJVupA"
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:postgresql://pg-fixitb-fix-itb.a.aivencloud.com:23836/defaultdb?ssl=require&user=avnadmin&password=AVNS_pGDnqLw3W1JnSBJVupA"
        val database = Database.connect(jdbcURL, driverClassName, user, password)


    }

    suspend fun <T> dbQuery(block: suspend () -> T): T {
        return newSuspendedTransaction(Dispatchers.IO) {
            block()
        }
    }
}