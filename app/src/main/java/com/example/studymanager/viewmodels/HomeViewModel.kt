package com.example.studymanager.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.studymanager.database.StudieDatabaseDAO
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import kotlinx.coroutines.*

class HomeViewModel(private val repository: StudieTaskRepository, application: Application) :
    AndroidViewModel(application) {

    private var _studieTasks = MutableLiveData<List<StudieTask>>()
    private val _navigateToStudieSessie = MutableLiveData<Int>()

    val tasks: LiveData<List<StudieTask>>
        get() = _studieTasks

    val navigateToStudiesessie
        get() = _navigateToStudieSessie

    init {
        initializeStudieTasks()
    }

    private fun initializeStudieTasks() {
        viewModelScope.launch {
            _studieTasks.value = getStudieTasksFromRepo()
        }
    }

    private suspend fun getStudieTasksFromRepo(): List<StudieTask> {
        return withContext(Dispatchers.IO) {
            val tasks = repository.getAllStudieTasks()
            tasks
        }
    }

    fun onStudieTaskClicked(id: Int) {
        _navigateToStudieSessie.value = id
    }

    fun onStudieTaskNavigated() {
        _navigateToStudieSessie.value = null
    }
}