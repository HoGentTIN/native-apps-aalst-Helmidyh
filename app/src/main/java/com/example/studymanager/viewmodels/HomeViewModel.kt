package com.example.studymanager.home

import android.app.Application
import androidx.lifecycle.*
import com.example.studymanager.database.getDatabase
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.DTO.StudieTaskDTO
import com.example.studymanager.models.DTO.StudieVakDTO
import com.example.studymanager.models.domain.StatsRepository
import com.example.studymanager.models.domain.StudieVakRepository
import kotlinx.coroutines.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val studieTaskRepository = StudieTaskRepository(database.studieTaskDAO, database.statsDAO)
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
            var vak = studieVakRepository.getStudieVak(task.vakId)
            vak.aantalTasks -= 1

            //update vak
            studieVakRepository.putStudieVak(StudieVakDTO(vak.studieVakId,vak.name,vak.aantalTasks,0))
            //update history
            statsRepository.putStats(StudieTaskDTO(task.studieTaskId,task.studieTaskTitle,task.totalTaskDuration,task.remainingTaskTime,task.vakId,task.vakName,0))
            //verwijder task
            studieTaskRepository.deleteStudieTask(task.studieTaskId)
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

