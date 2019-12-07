package com.example.studymanager.vakken

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studymanager.database.getDatabase
import com.example.studymanager.home.HomeViewModel

class VakkenViewModel(application:Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)


    fun onStudieVakClicked(id:Int){
    //    _navigateToStudieVak.value = id
    }

    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VakkenViewModel::class.java)) {
                return VakkenViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

