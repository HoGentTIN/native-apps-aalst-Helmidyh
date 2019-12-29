package com.example.studymanager.models.domain

import androidx.lifecycle.LiveData
import com.example.studymanager.App
import com.example.studymanager.database.StatsDAO
import com.example.studymanager.database.StudieVakDAO
import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.DTO.StudieTaskDTO
import com.example.studymanager.models.DTO.StudieVakDTO
import com.example.studymanager.network.StudieTaskService
import com.example.studymanager.network.StudieVakService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.InterruptedIOException

class StudieVakRepository(private val vakDAO: StudieVakDAO, private val statsDAO: StatsDAO) {

    fun getAllStudieVakken(): LiveData<List<StudieVak>> {
        return vakDAO.getAllVakken()
    }

    suspend fun getStudieVak(id: Int): StudieVak {
        return withContext(Dispatchers.IO) {
            vakDAO.get(id)
        }
    }

    suspend fun deleteStudieVak(vakId:Int): Int {
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()
        if (user != null) {

            return withContext(Dispatchers.IO) {
                val call = StudieVakService.HTTP.deleteStudieVak(vakId)

                val response = try {
                    val result = call.await()
                    vakDAO.delete(result.toModel())

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

    suspend fun putStudieVak(vak: StudieVakDTO): Int {
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()
        if (user != null) {
            val id = user.id
            vak.gebruikerId = id

            return withContext(Dispatchers.IO) {
                val call = StudieVakService.HTTP.putStudieVak(vak)

                val response = try {
                    val result = call.await()
                    vakDAO.update(result.toModel())

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

    suspend fun postStudieVak(vak: StudieVakDTO): Int {
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()
        if (user != null) {
            val id = user.id
            vak.gebruikerId = id

            return withContext(Dispatchers.IO) {
                val call = StudieVakService.HTTP.postStudieVak(vak)

                val response = try {
                    val result = call.await()
                     vakDAO.insert(result.toModel())

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

    suspend fun loadStudieVakken(): Boolean {
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()

        if (user != null) {
            val id = user.id

            return withContext(Dispatchers.IO) {
                val taskCall = StudieVakService.HTTP.getStudieVakkenForUser(id)
                try {
                    val tasks = taskCall.await()
                    vakDAO.clear()
                    vakDAO.insertAll(*tasks.map { x -> x.toModel() }.toTypedArray())

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