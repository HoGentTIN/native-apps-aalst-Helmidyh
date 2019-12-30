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
    /**
     * @property database = Database instantie die we altijd dezelfde instantie van application meegeven
     * @property statsRepository = Repository voor het bijhouden van algemene statistieken van de app, init via abstract type statsDao
     * @property vakkenRepository = Repository voor het bijhouden van studievakken via abstract type studievakDao
     * @property taskRepository = Repository voor het bijhouden van studietasks via abstract type studievakDao
     */
    private val database = getDatabase(application)
    private val taskRepository = StudieTaskRepository(database.studieTaskDAO)
    private val vakkenRepository = StudieVakRepository(database.studieVakDAO, database.statsDAO)
    private val statsRepository = StatsRepository(database.statsDAO)

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