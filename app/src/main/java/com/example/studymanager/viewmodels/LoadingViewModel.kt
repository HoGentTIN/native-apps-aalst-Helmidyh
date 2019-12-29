package com.example.studymanager.viewmodels

import android.app.Activity
import android.app.Application
import androidx.lifecycle.*
import com.example.studymanager.database.getDatabase
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.models.domain.StatsRepository
import com.example.studymanager.models.domain.StudieVakRepository
import com.example.studymanager.network.LocalDataHelper
import kotlinx.coroutines.launch

class LoadingViewModel(application: Application) :
    AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val taskRepository = StudieTaskRepository(database.studieTaskDAO, database.statsDAO)
    private val vakkenRepository = StudieVakRepository(database.studieVakDAO, database.statsDAO)
    private val statsRepository = StatsRepository(database.statsDAO)

    private val dataHelper = LocalDataHelper("setup", application)

    private val _loadingResult = MutableLiveData<Int>()
    val isLoading: LiveData<Int>
        get() = _loadingResult

    init {
        viewModelScope.launch {

            taskRepository.loadStudieTasks()
            vakkenRepository.loadStudieVakken()
            statsRepository.loadStats()

            _loadingResult.value = Activity.RESULT_OK
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoadingViewModel::class.java))
                return LoadingViewModel(application) as T

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}