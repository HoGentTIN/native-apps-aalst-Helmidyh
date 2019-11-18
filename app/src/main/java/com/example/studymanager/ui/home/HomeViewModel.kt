package com.example.studymanager.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.studymanager.database.StudieDatabaseDAO
import com.example.studymanager.domain.StudieTask
import kotlinx.coroutines.*

class HomeViewModel(val database: StudieDatabaseDAO, application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var studieTask = MutableLiveData<StudieTask?>()

    private val tasks = database.getAllTasks()

    init {
        initializeStudieTask()
    }

    private fun initializeStudieTask() {
        uiScope.launch {
            studieTask.value = getStudieTaskFromDatabase()
        }
    }

    private suspend fun getStudieTaskFromDatabase(): StudieTask? {
        return withContext(Dispatchers.IO) {
            //magelijks nog nullcheck toevoegen
            var studie = database.getMostRecentTask()
            studie
        }
    }

// logica voor het toevoegen moet in studiesessiecreatie zodat we nog kunnen cancellen als we in het creatiescherm zitten,


}