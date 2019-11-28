package com.example.studymanager.domain

import androidx.lifecycle.LiveData
import com.example.studymanager.database.StudieDatabaseDAO

class StudieTaskRepository(private val studieDAO: StudieDatabaseDAO) {


    fun getAllStudieTasks(): List<StudieTask> {
        return studieDAO.getAllTasks()
    }

    fun getStudieTask(id: Int): StudieTask {
        return studieDAO.get(id)
    }

    fun insert(studieTask: StudieTask) = studieDAO.insert(studieTask)

}