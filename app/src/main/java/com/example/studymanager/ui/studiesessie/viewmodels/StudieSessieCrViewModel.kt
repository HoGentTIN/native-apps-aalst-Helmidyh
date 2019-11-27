package com.example.studymanager.ui.studiesessie.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymanager.database.StudieDatabaseDAO
import com.example.studymanager.domain.StudieTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.R
import android.content.Context
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class StudieSessieCrViewModel(private val database: StudieDatabaseDAO, application: Application) : AndroidViewModel(application)  {

    init {

    }

    private suspend fun insert(task: StudieTask) {
        withContext(Dispatchers.IO) {
            database.insert(task)
        }
    }

        suspend fun createNewStudieTask(task:StudieTask){
                this.insert(task)
    }

    fun addDummy() {
        viewModelScope.launch {
            insert(StudieTask(0, "studeren voor Y", 200000, 20000, "Android"))
        }
    }
}
