package com.example.studymanager.stats

import android.app.Application
import androidx.lifecycle.*
import com.example.studymanager.database.getDatabase
import com.example.studymanager.models.domain.StatsRepository
import com.example.studymanager.models.domain.StudieVakRepository
import com.example.studymanager.vakken.VakkenViewModel
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * @property database = Database instantie die we altijd dezelfde instantie van application meegeven
     * @property statsRepository = Repository voor het bijhouden van algemene statistieken van de app, init via abstract type statsDao
     */
    private val database = getDatabase(application)
    private val statsRepository = StatsRepository(database.statsDAO)

    var meestGestVak = statsRepository.getMeestGestudeerdeVak()
    var minstGestVak = statsRepository.getMinstGestudeerdeVak()
    var totGestUur = statsRepository.getTotaalAantalGestudeerdeUren()

    /**
     * @property Factory = Maakt instantie's van StatsViewmodel aan via de meegegeven Application
     */
    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
                return StatsViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}