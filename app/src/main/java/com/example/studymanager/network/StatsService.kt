package com.example.studymanager.network

import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.DTO.StudieTaskDTO
import com.example.studymanager.models.DTO.StudieVakDTO
import com.example.studymanager.models.DTO.StudieVakHistoryDTO
import com.example.studymanager.models.domain.StudieVakHistory
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StatsApiService {

    @GET("stats/gebruiker/{id}")
    fun getStatsForUser(@Path("id") id: Int): Deferred<List<StudieVakHistoryDTO>>

    @POST("studieVakHistory")
    fun postStudieVakHistory(@Body studieVakHistoryDTO: StudieVakHistoryDTO): Deferred<StudieVakHistoryDTO>
}

object StatsService {
    val HTTP: StatsApiService by lazy { BaseService.RETROFIT.create(StatsApiService::class.java) }
}