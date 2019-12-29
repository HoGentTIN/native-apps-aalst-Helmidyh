package com.example.studymanager.domain

import androidx.lifecycle.LiveData
import com.example.studymanager.App
import com.example.studymanager.database.StatsDAO
import com.example.studymanager.database.StudieTaskDAO
import com.example.studymanager.models.DTO.StudieTaskDTO
import com.example.studymanager.network.StudieTaskService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.InterruptedIOException

class StudieTaskRepository(private val studieDAO: StudieTaskDAO, private val statsDAO: StatsDAO) {

    fun getAllStudieTasks(): LiveData<List<StudieTask>> {
        return studieDAO.getAllTasks()
    }

    fun getAllStudieTasksVoorVak(id: Int): LiveData<List<StudieTask>> {
        return studieDAO.getAllTasksForVak(id)

    }

    suspend fun getStudieTask(id: Int): StudieTask {
        return withContext(Dispatchers.IO) {
            studieDAO.get(id)
        }
    }

    // deze wordt gebruik om lokaal de tijd te laten aflopen ipv elke tick een post naar de api te sturen
    suspend fun update(studieTask: StudieTask) {
        withContext(Dispatchers.IO) {
            studieDAO.update(studieTask)
        }
    }

    suspend fun deleteStudieTask(taskId:Int): Int {
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()
        if (user != null) {

            return withContext(Dispatchers.IO) {
                val call = StudieTaskService.HTTP.deleteStudieTask(taskId)

                val response = try {
                    val result = call.await()
                    studieDAO.delete(result.toModel())

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

    suspend fun putStudieTask(task: StudieTaskDTO): Int {
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()
        if (user != null) {
            val id = user.id
            task.gebruikerId = id

            return withContext(Dispatchers.IO) {
                val call = StudieTaskService.HTTP.putStudieTask(task)

                val response = try {
                    val result = call.await()
                    studieDAO.update(result.toModel())

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

    suspend fun postStudieTask(task: StudieTaskDTO): Int {
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()
        if (user != null) {
            val id = user.id
            task.gebruikerId = id

            return withContext(Dispatchers.IO) {
                val call = StudieTaskService.HTTP.postStudieTask(task)

                val response = try {
                    val result = call.await()
                    studieDAO.insert(result.toModel())

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

    suspend fun loadStudieTasks(): Boolean {
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()

        if (user != null) {
            val id = user.id

            return withContext(Dispatchers.IO) {
                val taskCall = StudieTaskService.HTTP.getStudieTasksForUser(id)
                try {
                    val tasks = taskCall.await()
                    studieDAO.clear()
                    studieDAO.insertAll(*tasks.map { x -> x.toModel() }.toTypedArray())

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