package com.example.studymanager.network

import com.example.studymanager.models.DTO.StudieTaskDTO
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 * @property getStudieTasksForUser : Api request -> GET studieTask
 * @property deleteStudieTask : Api request -> DELETE studieTask
 * @property postStudieTask : Api request -> POST studieTask
 * @property putStudieTask : Api request -> PUT studieTask
 *
 */
interface StudieTaskApiService {

    @GET("studieTask/gebruiker/{id}")
    fun getStudieTasksForUser(@Path("id") id: Int): Deferred<List<StudieTaskDTO>>

    @DELETE("StudieTask/{id}")
    fun deleteStudieTask(@Path("id") id: Int): Deferred<StudieTaskDTO>

    @POST("StudieTask")
    fun postStudieTask(@Body studieTask: StudieTaskDTO): Deferred<StudieTaskDTO>

    @PUT("StudieTask")
    fun putStudieTask(@Body studieTask: StudieTaskDTO): Deferred<StudieTaskDTO>
}

object StudieTaskService {
    val HTTP: StudieTaskApiService by lazy { BaseService.RETROFIT.create(StudieTaskApiService::class.java) }
}