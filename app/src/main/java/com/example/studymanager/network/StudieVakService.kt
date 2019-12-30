package com.example.studymanager.network

import com.example.studymanager.models.DTO.StudieVakDTO
import kotlinx.coroutines.Deferred
import retrofit2.http.*
/**
 * @property getStudieVakkenForUser : Api request -> GET studieVak
 * @property deleteStudieVak : Api request -> DELETE studieVak
 * @property postStudieVak : Api request -> POST studieVak
 * @property putStudieVak : Api request -> PUT studieVak
 *
 */
interface StudieVakApiService {

    @GET("studieVak/gebruiker/{id}")
    fun getStudieVakkenForUser(@Path("id") id: Int): Deferred<List<StudieVakDTO>>

    @DELETE("StudieVak/{id}")
    fun deleteStudieVak(@Path("id") id: Int): Deferred<StudieVakDTO>

    @POST("StudieVak")
    fun postStudieVak(@Body studieVakDTO: StudieVakDTO): Deferred<StudieVakDTO>

    @PUT("StudieVak")
    fun putStudieVak(@Body studieVak: StudieVakDTO): Deferred<StudieVakDTO>
}

object StudieVakService {
    val HTTP: StudieVakApiService by lazy { BaseService.RETROFIT.create(StudieVakApiService::class.java) }
}