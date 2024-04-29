package com.fixitb.database.repositories

import com.fixitb.database.DatabaseFactory.dbQuery
import com.fixitb.database.dao.IncidenceDAO
import com.fixitb.models.Incidence
import com.fixitb.models.Incidences
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class IncidencesRepository: IncidenceDAO {
    private fun resultRowToIncidence(row: ResultRow) = Incidence(
        id = row[Incidences.id],
        device = row[Incidences.device],
        image = row[Incidences.image],
        description = row[Incidences.description],
        openDate = row[Incidences.openDate],
        closeDate = row[Incidences.closeDate],
        status = row[Incidences.status],
        classNum = row[Incidences.classNum],
        userAssigned = row[Incidences.userAssigned],
        codeMain = row[Incidences.codeMain],
        codeMovistar = row[Incidences.codeMovistar],
        userId = row[Incidences.userId],
        title = row[Incidences.title]

    )

    override suspend fun getIncidences(): List<Incidence> = dbQuery{
        Incidences.selectAll().map(::resultRowToIncidence)
    }

    override suspend fun insertIncidence(device: String, image: String, description: String, openDate: String, closeDate: String, status: String, classNum: Int, userAssigned: String, codeMain: String, codeMovistar: Int, userId: Int, title: String): Incidence? = dbQuery{
        val insertStatement = Incidences.insert {
            it[Incidences.device] = device
            it[Incidences.image] = image
            it[Incidences.description] = description
            it[Incidences.openDate] = openDate
            it[Incidences.closeDate] = closeDate
            it[Incidences.status] = status
            it[Incidences.classNum] = classNum
            it[Incidences.userAssigned] = userAssigned
            it[Incidences.codeMain] = codeMain
            it[Incidences.codeMovistar] = codeMovistar
            it[Incidences.userId] = userId
            it[Incidences.title] = title
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToIncidence)
    }

    override suspend fun getIncidencesByUserId(userId: Int): List<Incidence> = dbQuery {
        Incidences.select {Incidences.userId eq userId}.map(::resultRowToIncidence)
    }

    override suspend fun updateIncidenceById(
        incidenceId: Int,
        newDevice: String,
        newImage: String,
        newDescription: String,
        newStatus: String,
        newClassNum: Int,
        newUserAssigned: String,
        newCodeMain: String,
        newCodeMovistar: Int,
        newTitle: String,
        newcloseDate: String
    ): Boolean = dbQuery {
        val updateStatement = Incidences.update({Incidences.id eq incidenceId}){
            it[Incidences.device] = newDevice
            it[Incidences.image] = newImage
            it[Incidences.description] = newDescription
            it[Incidences.status] = newStatus
            it[Incidences.classNum] = newClassNum
            it[Incidences.userAssigned] = newUserAssigned
            it[Incidences.codeMain] = newCodeMain
            it[Incidences.codeMovistar] = newCodeMovistar
            it[Incidences.title] = newTitle
            it[Incidences.closeDate] = newcloseDate
        }
        updateStatement > 0
    }

    override suspend fun deleteIncidenceById(incidenceId: Int): Boolean = dbQuery {
        val deleteStatement = Incidences.deleteWhere { Incidences.id eq incidenceId }
        deleteStatement > 0
    }
}


