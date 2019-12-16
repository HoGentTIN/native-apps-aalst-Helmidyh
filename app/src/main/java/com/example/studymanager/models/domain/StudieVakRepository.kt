package com.example.studymanager.models.domain

import androidx.lifecycle.LiveData
import com.example.studymanager.database.StudieVakDAO
import com.example.studymanager.domain.StudieVak
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudieVakRepository(private val vakDAO: StudieVakDAO) {

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
            vakDAO.insert(studieVak)
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