package com.example.studymanager.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.studymanager.database.getDatabase
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import kotlinx.coroutines.launch

class VakSessiesViewmodel(application: Application) : AndroidViewModel(application){


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
        viewModelScope.launch {
            val tasks = studieTaskRepository.getAllStudieTasks()
            _studieTasks.value = tasks
        }
    }

    fun onStudieTaskClicked(id: Int) {
        _navigateToStudieSessie.value = id
    }

    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VakSessiesViewmodel::class.java)) {
                return VakSessiesViewmodel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}