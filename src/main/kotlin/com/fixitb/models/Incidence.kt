package com.fixitb.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ForeignKeyConstraint
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.Date
import org.jetbrains.exposed.sql.javatime.date

@Serializable
data class Incidence(
    val id: Int,
    val device: String,
    val image : String,
    val description: String,
    val openDate: String,
    val closeDate: String,
    val status: String,
    val classNum: Int,
    val userAssigned: String,
    val codeMain: String,
    val codeMovistar: Int,
    val userId: Int,
    val title: String
)


object Incidences : Table(){
    val id = integer("id").autoIncrement()
    val device = varchar("device", 15)
    val image = varchar("image", 255)
    val description = varchar("description", 255)
    val openDate = varchar("open_date", 255)
    val closeDate = varchar("close_date", 255)
    val status = varchar("status", 255)
    val classNum = integer("class_num")
    val userAssigned = varchar("user_assigned", 255)
    val codeMain = varchar("code_main", 255)
    val codeMovistar = integer("code_movistar")
    val userId = integer("user_id")
    val title = varchar("title", 255)

    override val primaryKey = PrimaryKey(id)


}