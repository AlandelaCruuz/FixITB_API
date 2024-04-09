package com.fixitb.database.repositories

import com.fixitb.database.DatabaseFactory.dbQuery
import com.fixitb.database.dao.IncidenceDAO
import com.fixitb.models.Incidence
import com.fixitb.models.Incidences
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

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

}