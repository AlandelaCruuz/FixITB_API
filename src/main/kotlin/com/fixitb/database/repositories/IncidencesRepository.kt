package com.fixitb.database.repositories

import com.fixitb.database.DatabaseFactory.dbQuery
import com.fixitb.database.dao.IncidenceDAO
import com.fixitb.models.Incidence
import com.fixitb.models.Incidences
import com.fixitb.models.Incidences.autoIncrement
import com.fixitb.models.User
import com.fixitb.models.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.selectAll
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
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
        classId = row[Incidences.classId],
        userAssigned = row[Incidences.userAssigned],
        codeMain = row[Incidences.codeMain],
        codeMovistar = row[Incidences.codeMovistar]

    )

    override suspend fun getIncidences(): List<Incidence> = dbQuery{
        Incidences.selectAll().map(::resultRowToIncidence)
    }

    override suspend fun insertIncidence(device: String, image: String, description: String, openDate: String, closeDate: String, status: String, classId: Int, userAssigned: String, codeMain: String, codeMovistar: Int): Incidence? = dbQuery{
        val insertStatement = Incidences.insert {
            it[Incidences.device] = device
            it[Incidences.image] = image
            it[Incidences.description] = description
            it[Incidences.openDate] = openDate
            it[Incidences.closeDate] = closeDate
            it[Incidences.status] = status
            it[Incidences.classId] = classId
            it[Incidences.userAssigned] = userAssigned
            it[Incidences.codeMain] = codeMain
            it[Incidences.codeMovistar] = codeMovistar
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToIncidence)
    }

}


