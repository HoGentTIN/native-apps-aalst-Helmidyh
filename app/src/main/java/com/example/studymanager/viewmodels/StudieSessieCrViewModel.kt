package com.example.studymanager.ui.studiesessie.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.studymanager.database.getDatabase
import com.example.studymanager.domain.StudieTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.home.HomeViewModel


class StudieSessieCrViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val studieTaskRepository = StudieTaskRepository(database.studieTaskDAO)

    init {
        //TODO opschonen
    }

    private suspend fun insert(task: StudieTask) {
        withContext(Dispatchers.IO) {
         studieTaskRepository.insert(task)
        }
    }

    fun createNewStudieTask(task: StudieTask) {
        viewModelScope.launch {
            insert(task)
        }
    }

    class Factory(
        private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StudieSessieCrViewModel::class.java)) {
                return StudieSessieCrViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
