package com.fixitb.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ForeignKeyConstraint
import org.jetbrains.exposed.sql.Table

@Serializable
data class Incidence(
    val id: Int,
    val device: String,
    val image : String,
    val description: String,
    val openDate: String,
    val closeDate: String,
    val status: String,
    val classId: Int,
    val userAssigned: String,
    val codeMain: Int,
    val codeMovistar: Int

)


object Incidences : Table(){
    val id = integer("id").autoIncrement()
    val device = varchar("device", 15)
    val image = varchar("image", 255)
    val description = varchar("description", 255)
    val openDate = varchar("open_date", 255)
    val closeDate = varchar("close_date", 150)
    val status = varchar("status", 255)
    val classId = integer("class_id")
    val userAssigned = varchar("user_assigned", 255)
    val codeMain = varchar("code_main", 255)
    val codeMovistar = integer("code_movistar")

    override val primaryKey = PrimaryKey(id)


}