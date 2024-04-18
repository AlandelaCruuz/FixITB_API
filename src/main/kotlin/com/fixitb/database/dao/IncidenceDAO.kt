package com.fixitb.database.dao

import com.fixitb.models.Incidence
import java.time.LocalDateTime
import java.util.Date

interface IncidenceDAO {
    suspend fun getIncidences(): List<Incidence>
    suspend fun insertIncidence(device: String, image: String, description: String, openDate: String, closeDate: String, status: String, classId: Int, userAssigned: String, codeMain: String, codeMovistar: Int): Incidence?

}