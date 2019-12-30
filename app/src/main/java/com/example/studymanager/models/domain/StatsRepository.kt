package com.example.studymanager.models.domain

import androidx.lifecycle.LiveData
import com.example.studymanager.App
import com.example.studymanager.database.StatsDAO
import com.example.studymanager.models.DTO.StudieTaskDTO
import com.example.studymanager.models.DTO.StudieVakHistoryDTO
import com.example.studymanager.network.StatsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.InterruptedIOException

class StatsRepository(private val stats: StatsDAO) {
    /**
     * We halen Het meest gestudeerde vak op van de lokale Database
     */
    fun getMeestGestudeerdeVak(): LiveData<String> {
        return stats.getMeestGestudeerdeVak()
    }

    /**
     * We halen Het meest gestudeerde vak op van de lokale Database
     */
    fun getMinstGestudeerdeVak(): LiveData<String> {
        return stats.geMinstGestudeerdeVak()
    }

    /**
     * We halen de som op van de totaal gestudeerde tijd per history
     */
    fun getTotaalAantalGestudeerdeUren(): LiveData<Long> {
        return stats.getTotaalAantalGestudeerdeUren()
    }

    /**
     * We halen de history op van het vak en updaten ze
     */
    suspend fun writeToHistory(task: StudieTaskDTO) {
        withContext(Dispatchers.IO) {
            putStats(task)
        }
    }

    /**
     * Post functie naar api
     */
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

    /**
     * Put functie naar api
     */
    suspend fun putStats(task: StudieTaskDTO): Int {
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()
        if (user != null) {
            val id = user.id
            task.gebruikerId = id

            return withContext(Dispatchers.IO) {

                // er wordt altijd een history aangemaakt per toegevoegd vak dus deze bestaat sws
                val x = stats.getVak(task.vakName) // deze mss naar ene call
                x.aantalTasks += 1
                x.totaleStudieTijd += task.totalTaskDuration // niet zeker of dit al werkt

                val call = StatsService.HTTP.putStudieVakHistory(StudieVakHistoryDTO(x.studieVakHistoryId, x.studieVakHistoryName, x.aantalTasks, x.totaleStudieTijd, id))

                val response = try {
                    val result = call.await()
                    stats.update(result.toModel())

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

    /**
     * Init functie die al de stats van een gebruiker ophaalt via de api
     */
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