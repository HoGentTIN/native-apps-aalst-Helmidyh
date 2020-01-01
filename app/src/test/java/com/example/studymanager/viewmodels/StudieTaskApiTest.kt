package com.example.studymanager.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.studymanager.database.StudieTaskDAO
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.models.DTO.StudieTaskDTO
import com.example.studymanager.models.domain.User
import com.example.studymanager.network.StudieTaskService
import com.example.studymanager.network.UserHelper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StudieTaskApiTest {

    // OKHTTP CLIENT certificate fout

    private var studieTaskService: StudieTaskService = mockk()

    private var studieTaskDAO: StudieTaskDAO = mockk()
    private var studieTaskRepository: StudieTaskRepository = mockk()

    private var tasks = MutableLiveData<List<StudieTask>>()

    private var task: StudieTask = StudieTask(0, "TestTask", 3600L, 3600L, 1, "TestVak")
    private var taskDTO: StudieTaskDTO = StudieTaskDTO(task.studieTaskId, task.studieTaskTitle, task.totalTaskDuration, task.remainingTaskTime, task.vakId, task.vakName, 1)
    private val id = 0

    private var userHelper: UserHelper = mockk()

    private var user: User = mockk()

    @Before
    fun setUp() {
        //Arrange

        studieTaskRepository = StudieTaskRepository(studieTaskDAO)
        coEvery { userHelper.getSignedInUser() } returns user
        coEvery { user.id } returns 1

        /**
         * API CALLS
         */

        coEvery { studieTaskService.HTTP.postStudieTask(taskDTO) } returns CompletableDeferred(taskDTO)
        coEvery { studieTaskService.HTTP.putStudieTask(taskDTO) } returns CompletableDeferred(taskDTO)

        /**
         * REPO CALLS
         */
        coEvery { studieTaskRepository.getStudieTask(id) } returns task
        coEvery { studieTaskRepository.getAllStudieTasks() } returns tasks
        /**
         * DAO CALLS
         */
        coEvery { studieTaskDAO.get(id) } returns task
        coEvery { studieTaskDAO.getAllTasks() } returns tasks
        coEvery { studieTaskDAO.update(task) }


    }

    @Test
    fun studieTaskApi_onPost_PostsToApi() {
        // Arrange
        runBlocking {
            coEvery { studieTaskRepository.postStudieTask(taskDTO, userHelper) } returns 200

            var x = studieTaskRepository.postStudieTask(taskDTO, userHelper)
            // var x = studieTaskService.HTTP.postStudieTask(taskDTO)

            coVerify {  studieTaskService.HTTP.postStudieTask(taskDTO) }

            // coVerify { studieTaskDAO.insert(task) }
            Assert.assertTrue(x == 200)
        }


    }
}