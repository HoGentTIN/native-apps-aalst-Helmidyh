package com.example.studymanager.repos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.studymanager.CoroutinesTestRule
import com.example.studymanager.database.StatsDAO
import com.example.studymanager.database.StudieVakDAO
import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.domain.StudieVakRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StudieVakkenRepoTest {

    private lateinit var studieVakRepository: StudieVakRepository
    private var studieVakDao: StudieVakDAO = mockk()
    private var statsDao: StatsDAO = mockk()

    private var vakken = MutableLiveData<List<StudieVak>>()

    private var vak: StudieVak = mockk()
    private val id = 0

    @Before
    fun setUp() {
        studieVakRepository = StudieVakRepository(studieVakDao,statsDao)


        /**
         * REPO CALLS
         */
        coEvery { studieVakRepository.getStudieVak(id) } returns vak
        coEvery { studieVakRepository.getAllStudieVakken() } returns vakken
        /**
         * DAO CALLS
         */
        coEvery { studieVakDao.get(id) } returns vak
        coEvery { studieVakDao.getAllVakken()} returns vakken
    }


    @Test
    fun repo_onGetVak_getsFromDAO() {
        runBlockingTest {
            //Act
            val repoVak = studieVakRepository.getStudieVak(id)
            //Assert
            coVerify { studieVakDao.get(id) }
            coVerify { studieVakRepository.getStudieVak(id) }
            Assert.assertEquals(vak, repoVak)
        }
    }


    @Test
    fun repo_onGetAllTasks_getsAllFromDAO() {
        runBlockingTest {
            //Act
            val repoTasks = studieVakRepository.getAllStudieVakken()
            //Assert
            coVerify { studieVakDao.getAllVakken() }
            coVerify { studieVakRepository.getAllStudieVakken() }
            Assert.assertEquals(vakken, repoTasks)
        }
    }

}