package com.example.studymanager.ui.studiesessie.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studymanager.studiesessie.StudieSessieViewModel

class StudieSessieViewModelFactory(private val time: Long, private val taskName: String) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudieSessieViewModel::class.java)) {
            return StudieSessieViewModel(time, taskName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}