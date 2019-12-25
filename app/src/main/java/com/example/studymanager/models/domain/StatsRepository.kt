package com.example.studymanager.models.domain

import androidx.lifecycle.LiveData
import com.example.studymanager.App
import com.example.studymanager.database.StatsDAO
import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.DTO.StudieVakDTO
import com.example.studymanager.models.DTO.StudieVakHistoryDTO
import com.example.studymanager.network.StatsService
import com.example.studymanager.network.StudieVakService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.InterruptedIOException

class StatsRepository(private val stats: StatsDAO) {

    fun getMeestGestudeerdeVak(): LiveData<String> {
        return stats.getMeestGestudeerdeVak()
    }

    fun getMinstGestudeerdeVak(): LiveData<String> {
        return stats.geMinstGestudeerdeVak()
    }

    fun getTotaalAantalGestudeerdeUren(): LiveData<Long> {
        return stats.getTotaalAantalGestudeerdeUren()
    }

    suspend fun postStats(history: StudieVakHistoryDTO): Int {
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()
        if (user != null) {
            val id = user.id
            history.gebruikerId = id

            return withContext(Dispatchers.IO) {
                val call = StatsService.HTTP.postStudieVakHistory(history)

                val response = try {
                    val result = call.await()
                    stats.insert(result.toModel())

                    200
                } catch (e: HttpException) {
                    e.printStackTrace()

                    e.code()
                } catch (e: InterruptedIOException) {
                    e.printStackTrace()

                    503
                } catch (e: Exception) {
                    e.printStackTrace()

                    -1
                }

                response

            }
        }

        return -1
    }

    suspend fun loadStats(): Boolean {
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()

        if (user != null) {
            val id = user.id

            return withContext(Dispatchers.IO) {
                val taskCall = StatsService.HTTP.getStatsForUser(id)
                try {
                    val historyItems = taskCall.await()
                    stats.clear()
                    stats.insertAll(*historyItems.map { x -> x.toModel() }.toTypedArray())

                    true
                } catch (e: HttpException) {
                    e.printStackTrace()

                    false
                } catch (e: Exception) {
                    e.printStackTrace()

                    false
                }
            }
        }

        return false
    }
}