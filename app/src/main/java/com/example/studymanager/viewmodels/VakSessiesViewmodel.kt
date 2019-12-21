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

    private var _studieTasks = MutableLiveData<List<StudieTask>>()

    val tasks: LiveData<List<StudieTask>>
        get() = _studieTasks


    init {
        initializeStudieTasks()
    }

    private fun initializeStudieTasks() {
        viewModelScope.launch {
            val tasks = studieTaskRepository.getAllStudieTasksVoorVak(vakId)
            _studieTasks.value = tasks
        }
    }

    class Factory(
        private val application: Application,private val vakId:Int
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VakSessiesViewmodel::class.java)) {
                return VakSessiesViewmodel(application,vakId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}