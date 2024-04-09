package com.fixitb.database.dao

import com.fixitb.models.Class


interface ClassDAO {
    suspend fun getClasses(): List<Class>
}