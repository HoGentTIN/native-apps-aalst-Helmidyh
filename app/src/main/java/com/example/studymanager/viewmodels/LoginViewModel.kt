package com.example.studymanager.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.studymanager.models.DTO.LoginDTO
import com.example.studymanager.network.AuthService
import com.example.studymanager.network.UserHelper
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.InterruptedIOException

class LoginViewModel(application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val userHelper = UserHelper(context)

    private val _loginResponse = MutableLiveData<Int>()
    val loginResponse: LiveData<Int>
        get() = _loginResponse

    fun loginUser(Email: String, password: String) {
        viewModelScope.launch {
            val loginCall = AuthService.HTTP.loginUser(LoginDTO(Email, password))
            try {
                val result = loginCall.await()
                userHelper.saveUser(result.authToken, result.afbeelding)
                _loginResponse.value = 200
            }
            catch (e: HttpException) {
                _loginResponse.value = e.code()
            }
            catch (e: InterruptedIOException) {
                _loginResponse.value = 504
            }
            catch (e: Exception) {
                _loginResponse.value = 400
                e.printStackTrace()
            }

            _loginResponse.value = null
        }
    }

    class Factory(private val application: Application): ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java))
                return LoginViewModel(application) as T

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}