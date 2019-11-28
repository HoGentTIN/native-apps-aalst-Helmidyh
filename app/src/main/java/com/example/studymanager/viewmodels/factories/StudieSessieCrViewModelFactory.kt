package com.example.studymanager.viewmodels.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studymanager.database.StudieDatabaseDAO
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.ui.studiesessie.viewmodels.StudieSessieCrViewModel

class StudieSessieCrViewModelFactory(
    private val dataSource:StudieTaskRepository,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudieSessieCrViewModel::class.java)) {
            return StudieSessieCrViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}