package com.example.studymanager.ui.studiesessie.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.studymanager.database.getDatabase
import com.example.studymanager.domain.StudieTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.home.HomeViewModel
import com.example.studymanager.models.DTO.StudieTaskDTO
import com.example.studymanager.models.domain.StudieVakRepository


class StudieSessieCrViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val studieVakRepository = StudieVakRepository(database.studieVakDAO, database.statsDAO)
    private val studieTaskRepository = StudieTaskRepository(database.studieTaskDAO, database.statsDAO)

    val vakken = studieVakRepository.getAllStudieVakken()

    init {
        //TODO opschonen
    }


    //update van vak vij creatie van studietask

     fun insert(task: StudieTask) {
        viewModelScope.launch {
            //hier ook in vak repository studietasks van vak ophalen en aan studietask aan toevoegen
            studieTaskRepository.postStudieTask(StudieTaskDTO(task.studieTaskId, task.studieTaskTitle, task.totalTaskDuration, task.remainingTaskTime, task.vakId, task.vakName, 0))
            updateVak(task.vakId)
            //moet mss async lopen idk
        }
    }

    fun updateVak(vakId: Int) {
        viewModelScope.launch {
            //   var x = studieTaskRepository.getAllStudieTasksVoorVak(vakId)
            var opgehaaldeVak = studieVakRepository.getStudieVak(vakId)
            opgehaaldeVak.aantalTasks += 1
            studieVakRepository.update(opgehaaldeVak)
        }
    }


    class Factory(
        private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StudieSessieCrViewModel::class.java)) {
                return StudieSessieCrViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
