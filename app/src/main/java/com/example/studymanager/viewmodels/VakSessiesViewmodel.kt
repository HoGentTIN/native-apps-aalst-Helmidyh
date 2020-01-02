package com.example.studymanager.viewmodels

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
 * @property studieTaskRepository = Repository voor het bijhouden van tasks, init via abstract type studieVakDao
 */
class VakSessiesViewmodel(
    private val vakId: Int,
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
            statsRepository.writeToHistory(
                StudieTaskDTO(
                    task.studieTaskId,
                    task.studieTaskTitle,
                    task.totalTaskDuration,
                    task.remainingTaskTime,
                    task.vakId,
                    task.vakName,
                    0
                )
            )
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
                studieVakRepository.putStudieVak(
                    StudieVakDTO(
                        vak.studieVakId,
                        vak.name,
                        vak.aantalTasks,
                        0
                    )
                )
            }

        }
    }


    class Factory(
        private val vakId: Int,
        private val studieTaskRepository: StudieTaskRepository,
        private val studieVakRepository: StudieVakRepository,
        private val statsRepository: StatsRepository
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VakSessiesViewmodel::class.java)) {
                return VakSessiesViewmodel(vakId, studieTaskRepository, studieVakRepository,statsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}