package com.example.studymanager.network

import com.example.studymanager.models.DTO.StudieVakHistoryDTO
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 * @property getStatsForUser : Api request -> GET stats
 * @property postStudieVakHistory : Api request -> POST history
 * @property putStudieVakHistory : Api request -> PUT history
 *
 */
interface StatsApiService {

    @GET("stats/gebruiker/{id}")
    fun getStatsForUser(@Path("id") id: Int): Deferred<List<StudieVakHistoryDTO>>

    @POST("stats")
    fun postStudieVakHistory(@Body studieVakHistoryDTO: StudieVakHistoryDTO): Deferred<StudieVakHistoryDTO>

    @PUT("stats")
    fun putStudieVakHistory(@Body studieVakHistoryDTO: StudieVakHistoryDTO): Deferred<StudieVakHistoryDTO>
}

object StatsService {
    val HTTP: StatsApiService by lazy { BaseService.RETROFIT.create(StatsApiService::class.java) }
}