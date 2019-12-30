package com.example.studymanager.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studymanager.models.domain.StatsRepository
/**
 * @property statsRepository = Repository voor het bijhouden van algemene statistieken van de app, init via abstract type statsDao
 */
class StatsViewModel(private val statsRepository:StatsRepository) : ViewModel() {

    var meestGestVak = statsRepository.getMeestGestudeerdeVak()
    var minstGestVak = statsRepository.getMinstGestudeerdeVak()
    var totGestUur = statsRepository.getTotaalAantalGestudeerdeUren()

    /**
     * @property Factory = Maakt instantie's van StatsViewmodel aan via de meegegeven Application
     */
    class Factory(
        private val statsRepository:StatsRepository
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
                return StatsViewModel(statsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}