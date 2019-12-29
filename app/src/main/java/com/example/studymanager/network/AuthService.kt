package com.example.studymanager.network

import com.example.studymanager.models.DTO.LoginDTO
import com.example.studymanager.models.DTO.RegisterDTO
import com.example.studymanager.models.DTO.UserDTO
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    fun loginUser(@Body dto: LoginDTO): Deferred<UserDTO>

    @POST("auth/register")
    fun registerUser(@Body dto: RegisterDTO): Deferred<UserDTO>

}

object AuthService {
    val HTTP : AuthApiService by lazy { BaseService.RETROFIT.create(AuthApiService::class.java) }
}