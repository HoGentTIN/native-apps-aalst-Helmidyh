package com.example.studymanager.domain

import androidx.lifecycle.LiveData
import com.example.studymanager.database.StatsDAO
import com.example.studymanager.database.StudieTaskDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudieTaskRepository(private val studieDAO: StudieTaskDAO, private val statsDAO: StatsDAO) {

    fun getAllStudieTasks(): LiveData<List<StudieTask>> {
        return studieDAO.getAllTasks()
    }

    fun getAllStudieTasksVoorVak(id: Int): LiveData<List<StudieTask>> {
        return studieDAO.getAllTasksForVak(id)

    }

    suspend fun getStudieTask(id: Int): StudieTask {
        return withContext(Dispatchers.IO) {
            studieDAO.get(id)
        }
    }

    suspend fun insert(studieTask: StudieTask) {
        withContext(Dispatchers.IO) {
            studieDAO.insert(studieTask)
        }
    }

    suspend fun delete(studieTask: StudieTask) {
        //vak ophalen van task
        // count toev
        withContext(Dispatchers.IO) {
            val x = statsDAO.getVak(studieTask.vakName)
            x.aantalTasks += 1
            x.totaleStudieTijd += x.totaleStudieTijd // niet zeker of dit al werkt
            statsDAO.update(x)
            studieDAO.delete(studieTask)
        }
    }

    suspend fun update(studieTask: StudieTask) {
        withContext(Dispatchers.IO) {
            studieDAO.update(studieTask)
        }
    }
}