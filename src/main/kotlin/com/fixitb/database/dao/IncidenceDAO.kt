package com.fixitb.database.dao

import com.fixitb.models.Incidence

interface IncidenceDAO {
    suspend fun getIncidences(): List<Incidence>
}