package com.example.studymanager.repos

import androidx.lifecycle.MutableLiveData
import com.example.studymanager.database.StatsDAO
import com.example.studymanager.models.domain.StatsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StatsRepoTest {
    private lateinit var statsRepository: StatsRepository
    private var statsDAO: StatsDAO = mockk()

    private val meestGestVak = MutableLiveData<String>("MeestGestVak")
    private val minstGestVak = MutableLiveData<String>("MinstGestVak")


    @Before
    fun setUp() {

        statsRepository = StatsRepository(statsDAO)

        /**
         * REPO CALLS
         */
        coEvery { statsRepository.getMeestGestudeerdeVak() } returns meestGestVak
        coEvery { statsRepository.getMinstGestudeerdeVak() } returns minstGestVak
        /**
         * DAO CALLS
         */
        coEvery { statsDAO.getMeestGestudeerdeVak() } returns meestGestVak
        coEvery { statsDAO.geMinstGestudeerdeVak() } returns minstGestVak

    }


    @Test
    fun repo_onGetMeestGestVak_getsFromDAO() {
        runBlockingTest {
            //Act
            val repoStat = statsRepository.getMeestGestudeerdeVak()
            //Assert
            coVerify { statsDAO.getMeestGestudeerdeVak() }
            coVerify { statsRepository.getMeestGestudeerdeVak() }
            Assert.assertEquals(meestGestVak, repoStat)
        }
    }

    @Test
    fun repo_onGetMinstGestVak_getsFromDAO() {
        runBlockingTest {
            //Act
            val repoStat = statsRepository.getMinstGestudeerdeVak()
            //Assert
            coVerify { statsDAO.geMinstGestudeerdeVak() }
            coVerify { statsRepository.getMinstGestudeerdeVak() }
            Assert.assertEquals(minstGestVak, repoStat)
        }
    }


}