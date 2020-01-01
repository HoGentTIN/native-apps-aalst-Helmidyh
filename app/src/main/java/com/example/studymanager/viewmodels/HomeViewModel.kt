package com.example.studymanager.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.models.DTO.StudieTaskDTO
import com.example.studymanager.models.DTO.StudieVakDTO
import com.example.studymanager.models.domain.StatsRepository
import com.example.studymanager.models.domain.StudieVakRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @property statsRepository = Repository voor het bijhouden van algemene statistieken van de app, init via abstract type statsDao
 * @property studieVakRepository = Repository voor het bijhouden van studievakken via abstract type studievakDao
 * @property studieTaskRepository = Repository voor het bijhouden van studietasks via abstract type studievakDao
 */
class HomeViewModel(
    private val studieTaskRepository: StudieTaskRepository,
    private val studieVakRepository: StudieVakRepository,
    private val statsRepository: StatsRepository
) : ViewModel() {

    var tasks = studieTaskRepository.getAllStudieTasks()

    fun onStudieTaskLongClicked(taskId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val clickedTask: StudieTask = studieTaskRepository.getStudieTask(taskId)
                delete(clickedTask)
            }
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
            withContext(Dispatchers.IO) {
                var vak = studieVakRepository.getStudieVak(vakId)
                vak.aantalTasks -= 1
                studieVakRepository.putStudieVak(StudieVakDTO(vak.studieVakId, vak.name, vak.aantalTasks, 0))
            }

        }
    }


    class Factory(
        private val studieTaskRepository: StudieTaskRepository,
        private val studieVakRepository: StudieVakRepository,
        private val statsRepository: StatsRepository
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(studieTaskRepository, studieVakRepository, statsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

