package com.example.studymanager.ui.studiesessie.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.models.DTO.StudieTaskDTO
import com.example.studymanager.models.DTO.StudieVakDTO
import com.example.studymanager.models.domain.StudieVakRepository
import kotlinx.coroutines.launch

/**
 * @property studieVakRepository = Repository voor het bijhouden van vakken, init via abstract type studieVakDao
 * @property studieTaskRepository = Repository voor het bijhouden van tasks, init via abstract type studieVakDao
 */
class StudieSessieCrViewModel(private val studieVakRepository: StudieVakRepository, private val studieTaskRepository: StudieTaskRepository) : ViewModel() {

    val vakken = studieVakRepository.getAllStudieVakken()

    /**
     * Bij create van een nieuwe studiesessie moeten we het vak van die sessie updaten (taskAmount)
     */
    fun insert(task: StudieTask) {
        viewModelScope.launch {
            studieTaskRepository.postStudieTask(StudieTaskDTO(task.studieTaskId, task.studieTaskTitle, task.totalTaskDuration, task.remainingTaskTime, task.vakId, task.vakName, 0))
            updateVak(task.vakId)
        }
    }

    fun updateVak(vakId: Int) {
        viewModelScope.launch {
            val vak = studieVakRepository.getStudieVak(vakId)
            vak.aantalTasks += 1
            studieVakRepository.putStudieVak(StudieVakDTO(vak.studieVakId, vak.name, vak.aantalTasks, 0))
        }
    }


    class Factory(
        private val studieVakRepository: StudieVakRepository, private val studieTaskRepository: StudieTaskRepository
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StudieSessieCrViewModel::class.java)) {
                return StudieSessieCrViewModel(studieVakRepository, studieTaskRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
