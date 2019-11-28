package com.example.studymanager.viewmodels.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studymanager.database.StudieDatabaseDAO
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.studiesessie.StudieSessieViewModel

class StudieSessieViewModelFactory(private val taskId: Int, private val datasource: StudieTaskRepository, private val application: Application) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudieSessieViewModel::class.java)) {
            return StudieSessieViewModel(taskId, datasource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}