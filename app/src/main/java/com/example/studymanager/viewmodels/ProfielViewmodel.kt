package com.example.studymanager.account

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studymanager.network.UserHelper
import com.example.studymanager.network.UserPictureHelper
import com.example.studymanager.stats.StatsViewModel

class ProfielViewmodel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    val currentUser = UserHelper(context).getSignedInUser()!!.Email
    var imgBitmap: Bitmap?

    init {
        //mss default image nog doen ipv force cast
        this.imgBitmap = UserPictureHelper.decodeImage(UserHelper(context).getSignedInUser()!!.afbeelding)
    }


    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfielViewmodel::class.java)) {
                return ProfielViewmodel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}