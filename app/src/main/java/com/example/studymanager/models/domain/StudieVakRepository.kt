package com.example.studymanager.models.domain

import androidx.lifecycle.LiveData
import com.example.studymanager.database.StatsDAO
import com.example.studymanager.database.StudieVakDAO
import com.example.studymanager.domain.StudieVak
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudieVakRepository(private val vakDAO: StudieVakDAO, private val statsDAO: StatsDAO) {

    fun getAllStudieVakken(): LiveData<List<StudieVak>> {
        return vakDAO.getAllVakken()
    }

    suspend fun getStudieVak(id: Int): StudieVak {
        return withContext(Dispatchers.IO) {
            vakDAO.get(id)
        }
    }

    suspend fun insert(studieVak: StudieVak) {
        withContext(Dispatchers.IO) {
            val x = vakDAO.get(studieVak.name)
            if (x == null) {
                vakDAO.insert(studieVak)
            }
            val m = statsDAO.getVak(studieVak.name)
            if (m == null) {
                statsDAO.insert(StudieVakHistory(studieVak.name, 0, 0L))
            }
        }
    }

    suspend fun delete(studieVak: StudieVak) {
        withContext(Dispatchers.IO) {
            vakDAO.delete(studieVak)
        }
    }

    suspend fun update(studieVak: StudieVak) {
        withContext(Dispatchers.IO) {
            vakDAO.update(studieVak)
        }
    }
}