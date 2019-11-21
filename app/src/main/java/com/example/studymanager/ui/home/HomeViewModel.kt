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

class HomeViewModel(val database: StudieDatabaseDAO, application: Application) :
    AndroidViewModel(application) {

    private var studieTask = MutableLiveData<StudieTask?>()
    private var _studieTasks = MutableLiveData<List<StudieTask>>()
    val tasks: LiveData<List<StudieTask>>
        get() = _studieTasks

    init {
        initializeStudieTasks()
        initializeStudieTask()
    }

    private fun initializeStudieTasks() {
        viewModelScope.launch {
            _studieTasks.value = getStudieTasksFromDatabase()
        }
    }

    private fun initializeStudieTask() {
        viewModelScope.launch {
            studieTask.value = getStudieTaskFromDatabase(1)
        }
    }

    private suspend fun getStudieTaskFromDatabase(id: Int): StudieTask? {
        return withContext(Dispatchers.IO) {
            var studie = database.getMostRecentTask()
            studie
        }
    }

    private suspend fun getStudieTasksFromDatabase(): List<StudieTask> {
        return withContext(Dispatchers.IO) {
            var tasks = database.getAllTasks()
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
            insert(StudieTask(0, "studeren voor x", 200000, 20000, "Android"))
        }
    }
// logica voor het toevoegen moet in studiesessiecreatie zodat we nog kunnen cancellen als we in het creatiescherm zitten,


}