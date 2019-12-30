package com.example.studymanager.home

import android.app.Application
import androidx.lifecycle.*
import com.example.studymanager.database.getDatabase
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.DTO.StudieTaskDTO
import com.example.studymanager.models.DTO.StudieVakDTO
import com.example.studymanager.models.DTO.StudieVakHistoryDTO
import com.example.studymanager.models.domain.StatsRepository
import com.example.studymanager.models.domain.StudieVakRepository
import kotlinx.coroutines.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * @property database = Database instantie die we altijd dezelfde instantie van application meegeven
     * @property statsRepository = Repository voor het bijhouden van algemene statistieken van de app, init via abstract type statsDao
     * @property studieVakRepository = Repository voor het bijhouden van studievakken via abstract type studievakDao
     * @property studieTaskRepository = Repository voor het bijhouden van studietasks via abstract type studievakDao
     */
    private val database = getDatabase(application)
    private val studieTaskRepository = StudieTaskRepository(database.studieTaskDAO)
    private val studieVakRepository = StudieVakRepository(database.studieVakDAO, database.statsDAO)
    private val statsRepository = StatsRepository(database.statsDAO)

    var tasks = studieTaskRepository.getAllStudieTasks()


    fun onStudieTaskLongClicked(taskId: Int) {
        viewModelScope.launch {
            val clickedTask: StudieTask = studieTaskRepository.getStudieTask(taskId)
            delete(clickedTask)
        }
    }

    private fun delete(task: StudieTask) {
        viewModelScope.launch {
            /* Update vak omdat we verwijderen */
            updateVak(task.vakId)
            /* Pas de history van het vak aan */
            statsRepository.writeToHistory(StudieTaskDTO(task.studieTaskId, task.studieTaskTitle, task.totalTaskDuration, task.remainingTaskTime, task.vakId, task.vakName, 0))
            /* Verwijder de task */
            studieTaskRepository.deleteStudieTask(task.studieTaskId)
        }
    }

    /**
     * Bij het verwijderen van een task moeten we het vak aanpassen (taskAmount)
     */
    fun updateVak(vakId: Int) {
        viewModelScope.launch {
            var vak = studieVakRepository.getStudieVak(vakId)
            vak.aantalTasks -= 1
            studieVakRepository.putStudieVak(StudieVakDTO(vak.studieVakId, vak.name, vak.aantalTasks, 0))
        }
    }


    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

