package com.fixitb.database.dao

import com.fixitb.models.Incidence
import java.time.LocalDateTime
import java.util.Date

interface IncidenceDAO {
    suspend fun getIncidences(): List<Incidence>
    suspend fun insertIncidence(device: String, image: String, description: String, openDate: String, closeDate: String, status: String, classNum: Int, userAssigned: String, codeMain: String, codeMovistar: Int, user_id: Int, title: String): Incidence?
    suspend fun getIncidencesByUserId(userId: Int): List<Incidence>
    suspend fun updateIncidenceById(incidenceId: Int, device: String, image: String, description: String, status: String, classNum: Int, userAssigned: String, codeMain: String, codeMovistar: Int, title: String, closeDate: String): Boolean
    suspend fun deleteIncidenceById(incidenceId: Int): Boolean
}