package com.example.studymanager.network

import com.example.studymanager.models.DTO.StudieTaskDTO
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudieTaskApiService {

    @GET("studieTask/gebruiker/{id}")
    fun getStudieTasksForUser(@Path("id") id: Int): Deferred<List<StudieTaskDTO>>

    @POST("studieTask")
    fun postStudieTask(@Body studieTask: StudieTaskDTO): Deferred<StudieTaskDTO>
}

object StudieTaskService {
    val HTTP: StudieTaskApiService by lazy { BaseService.RETROFIT.create(StudieTaskApiService::class.java) }
}