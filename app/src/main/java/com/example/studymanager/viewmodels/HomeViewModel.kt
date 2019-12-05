package com.example.studymanager.home

import android.app.Application
import androidx.lifecycle.*
import com.example.studymanager.database.getDatabase
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import kotlinx.coroutines.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val studieTaskRepository = StudieTaskRepository(database.studieTaskDAO)

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
        getStudieTasksFromRepo()
    }

    fun getStudieTasksFromRepo() {
        viewModelScope.launch {
            val tasks = studieTaskRepository.getAllStudieTasks()
            _studieTasks.value = tasks
        }
    }

    fun onStudieTaskClicked(id: Int) {
        _navigateToStudieSessie.value = id
    }

    //  fun onStudieTaskNavigated() {
    //      _navigateToStudieSessie.value = null
    //  }

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

