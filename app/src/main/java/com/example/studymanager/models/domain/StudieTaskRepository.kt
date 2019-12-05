package com.example.studymanager.domain

import com.example.studymanager.database.StudieTaskDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudieTaskRepository(private val studieDAO: StudieTaskDAO) {


    suspend fun getAllStudieTasks(): List<StudieTask> {
        return withContext(Dispatchers.IO){
            studieDAO.getAllTasks()
        }
    }

    fun getStudieTask(id: Int): StudieTask {
        return studieDAO.get(id)
    }

    fun insert(studieTask: StudieTask) = studieDAO.insert(studieTask)

    suspend fun update(studieTask: StudieTask) {
        withContext(Dispatchers.IO) {
            studieDAO.update(studieTask)
        }
    }
}