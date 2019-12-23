package com.example.studymanager.home

import android.app.Application
import androidx.lifecycle.*
import com.example.studymanager.database.getDatabase
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.domain.StudieVak
import kotlinx.coroutines.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val studieTaskRepository = StudieTaskRepository(database.studieTaskDAO, database.statsDAO)

    private var _studieTasks = MutableLiveData<List<StudieTask>>()

    val tasks: LiveData<List<StudieTask>>
        get() = _studieTasks


    init {
        initializeStudieTasks()
    }

    private fun initializeStudieTasks() {
        viewModelScope.launch {
            val tasks = studieTaskRepository.getAllStudieTasks()
            _studieTasks.value = tasks
        }
    }

    fun onStudieTaskLongClicked(taskId: Int) {
        viewModelScope.launch {
            val clickedTask: StudieTask = studieTaskRepository.getStudieTask(taskId)
            delete(clickedTask)
        }
    }

    private fun delete(task: StudieTask) {
        viewModelScope.launch {
            studieTaskRepository.delete(task)
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

