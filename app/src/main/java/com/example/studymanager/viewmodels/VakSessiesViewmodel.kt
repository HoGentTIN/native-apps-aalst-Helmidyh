package com.example.studymanager.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.studymanager.database.getDatabase
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import kotlinx.coroutines.launch

class VakSessiesViewmodel(application: Application, private val vakId: Int) : AndroidViewModel(application) {


    private val database = getDatabase(application)
    private val studieTaskRepository = StudieTaskRepository(database.studieTaskDAO)

    var tasks = studieTaskRepository.getAllStudieTasks()


    fun onStudieTaskLongClicked(taskId: Int) {
        viewModelScope.launch {
            val clickedTask: StudieTask = studieTaskRepository.getStudieTask(taskId)
            delete(clickedTask)
        }
    }

    private fun delete(task: StudieTask) {
        viewModelScope.launch {
            studieTaskRepository.deleteStudieTask(task.studieTaskId)
        }
    }

    class Factory(
        private val application: Application, private val vakId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VakSessiesViewmodel::class.java)) {
                return VakSessiesViewmodel(application, vakId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}