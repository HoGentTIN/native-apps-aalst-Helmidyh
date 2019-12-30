package com.example.studymanager.viewmodels

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.example.studymanager.models.DTO.RegisterDTO
import com.example.studymanager.network.AuthService
import com.example.studymanager.network.UserHelper
import com.example.studymanager.network.UserPictureHelper

import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.InterruptedIOException

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val userHelper = UserHelper(context)

    private val _registerResponse = MutableLiveData<Int>()
    val registerResponse: LiveData<Int>
        get() = _registerResponse

    fun registerUser(
        Email: String,
        wachtwoord: String,
        wachtwoordHerhaling: String
    ) {
        viewModelScope.launch {
            val registerCall = AuthService.HTTP.registerUser(RegisterDTO(pictureString, Email, wachtwoord, wachtwoordHerhaling))
            try {
                val result = registerCall.await()
                userHelper.saveUser(result.authToken, result.afbeelding)
                _registerResponse.value = 200
            } catch (e: HttpException) {
                _registerResponse.value = e.code()
            } catch (e: InterruptedIOException) {
                _registerResponse.value = 504
            } catch (e: Exception) {
                _registerResponse.value = 0
                e.printStackTrace()
            }
        }
    }

    private var pictureString: String? = null

    var picture: Bitmap? = null
    var canClearPicture = ObservableBoolean()

    fun changePicture(uri: Uri?) {
        if (uri == null) {
            picture = null
            pictureString = null
            canClearPicture.set(false)

            return
        }

        val stream = context.contentResolver.openInputStream(uri)
        if (stream != null) {
            pictureString = UserPictureHelper.encodePicture(stream)
            if (pictureString != null) {
                picture = UserPictureHelper.decodeImage(pictureString!!)
                canClearPicture.set(true)
            }
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java))
                return RegisterViewModel(application) as T

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}