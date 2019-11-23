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

class HomeViewModel(private val database: StudieDatabaseDAO, application: Application) :
    AndroidViewModel(application) {

    private var _studieTasks = MutableLiveData<List<StudieTask>>()
    val tasks: LiveData<List<StudieTask>>
        get() = _studieTasks

    private val _navigateToStudieSessie = MutableLiveData<Int>()
    val navigateToStudiesessie
        get() = _navigateToStudieSessie

    init {
        initializeStudieTasks()
    }

    private fun initializeStudieTasks() {
        viewModelScope.launch {
            _studieTasks.value = getStudieTasksFromDatabase()
        }
    }

    private suspend fun getStudieTasksFromDatabase(): List<StudieTask> {
        return withContext(Dispatchers.IO) {
            val tasks = database.getAllTasks()
            tasks
        }

    }

    private suspend fun insert(task: StudieTask) {
        withContext(Dispatchers.IO) {
            database.insert(task)
        }
    }

    fun addDummy() {
        viewModelScope.launch {
            insert(StudieTask(0, "studeren voor Y", 200000, 20000, "Android"))
        }
    }

    fun onStudieTaskClicked(id: Int) {
        _navigateToStudieSessie.value = id
    }

    fun onStudieTaskNavigated() {
        _navigateToStudieSessie.value = null
    }
// logica voor het toevoegen moet in studiesessiecreatie zodat we nog kunnen cancellen als we in het creatiescherm zitten,


}