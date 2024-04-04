package com.fixitb.models
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Class(
    val id: Int,
    val classNum: Int
)

//tablas
object Classes : Table(){
    val id = integer("id").autoIncrement()
    val classNum = integer("class_num")

    override val primaryKey = PrimaryKey(id)

}

