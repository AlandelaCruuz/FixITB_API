package com.fixitb.models

import com.fixitb.models.Classes.autoIncrement
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(
    val id: Int,
    val role: String,
    val email: String,
    val classId: Int,
)


object Users : Table(){
    val id = integer("id").autoIncrement()
    val role = varchar("role", 255)
    val email = varchar("email", 255)
    val classId = integer("class_id")

    override val primaryKey = PrimaryKey(id)

}