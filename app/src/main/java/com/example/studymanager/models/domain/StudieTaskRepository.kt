package com.example.studymanager.domain

import com.example.studymanager.database.StudieTaskDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudieTaskRepository(private val studieDAO: StudieTaskDAO) {

    suspend fun getAllStudieTasks(): List<StudieTask> {
        return withContext(Dispatchers.IO) {
            studieDAO.getAllTasks()
        }
    }

    suspend fun getAllStudieTasksVoorVak(id: Int): List<StudieTask> {
        return withContext(Dispatchers.IO) {
          studieDAO.getAllTasksForVak(id);

        }
    }

    fun getStudieTask(id: Int): StudieTask {
        return studieDAO.get(id)
    }

    suspend fun insert(studieTask: StudieTask) {
        withContext(Dispatchers.IO){
        studieDAO.insert(studieTask)
        }
    }

    suspend fun update(studieTask: StudieTask) {
        withContext(Dispatchers.IO) {
            studieDAO.update(studieTask)
        }
    }
}