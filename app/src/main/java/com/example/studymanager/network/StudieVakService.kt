package com.example.studymanager.network

import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.DTO.StudieTaskDTO
import com.example.studymanager.models.DTO.StudieVakDTO
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudieVakApiService {

    @GET("studieVak/gebruiker/{id}")
    fun getStudieVakkenForUser(@Path("id") id: Int): Deferred<List<StudieVakDTO>>

    @POST("studieVak")
    fun postStudieVak(@Body studieVakDTO: StudieVakDTO): Deferred<StudieVakDTO>
}

object StudieVakService {
    val HTTP: StudieVakApiService by lazy { BaseService.RETROFIT.create(StudieVakApiService::class.java) }
}