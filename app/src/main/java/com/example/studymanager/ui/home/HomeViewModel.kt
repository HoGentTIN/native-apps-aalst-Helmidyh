package com.example.studymanager.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.studymanager.database.StudieDatabaseDAO

class HomeViewModel(val database: StudieDatabaseDAO, application: Application) :
    AndroidViewModel(application) {


}