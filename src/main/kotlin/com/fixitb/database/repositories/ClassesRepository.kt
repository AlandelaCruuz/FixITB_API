package com.fixitb.database.repositories

import com.fixitb.database.DatabaseFactory.dbQuery
import com.fixitb.database.dao.ClassDAO
import com.fixitb.models.Class
import com.fixitb.models.Classes
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class ClassesRepository: ClassDAO {

    private fun resultRowToClass(row: ResultRow) = Class(
        id = row[Classes.id],
        classNum = row[Classes.classNum]
    )

    override suspend fun getClasses() : List<Class> = dbQuery{
        Classes.selectAll().map(::resultRowToClass)
    }
}