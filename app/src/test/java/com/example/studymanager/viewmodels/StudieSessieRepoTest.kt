package com.example.studymanager.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.studymanager.database.StudieTaskDAO
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StudieSessieRepoTest {
    // private lateinit var viewModel: StudieSessieViewModel
    private var studieTaskDAO: StudieTaskDAO = mockk()
    private lateinit var studieTaskRepository: StudieTaskRepository

    private var tasks = MutableLiveData<List<StudieTask>>()

    private var task: StudieTask = mockk()
    private val id = 0


    @Before
    fun setUp() {
        //Arrange
        studieTaskRepository = StudieTaskRepository(studieTaskDAO)

        /**
         * REPO CALLS
         */
        coEvery { studieTaskRepository.getStudieTask(id) } returns task
        coEvery { studieTaskRepository.getAllStudieTasks() } returns tasks
        /**
         * DAO CALLS
         */
        coEvery { studieTaskDAO.get(id) } returns task
        coEvery { studieTaskDAO.getAllTasks()} returns tasks
    }

    @Test
    fun repo_onGetTask_getsFromDAO() {
        runBlockingTest {
            //Act
            val repoTask = studieTaskRepository.getStudieTask(id)
            //Assert
            coVerify { studieTaskDAO.get(id) }
            coVerify { studieTaskRepository.getStudieTask(id) }
            Assert.assertEquals(task, repoTask)
        }
    }

    @Test
    fun repo_onGetAllTasks_getsAllFromDAO() {
        runBlockingTest {
            //Act
            val repoTasks = studieTaskRepository.getAllStudieTasks()
            //Assert
            coVerify { studieTaskDAO.getAllTasks() }
            coVerify { studieTaskRepository.getAllStudieTasks() }
            Assert.assertEquals(tasks, repoTasks)
        }
    }

}