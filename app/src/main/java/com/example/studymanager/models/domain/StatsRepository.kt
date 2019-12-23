package com.example.studymanager.models.domain

import androidx.lifecycle.LiveData
import com.example.studymanager.database.StatsDAO
import com.example.studymanager.domain.StudieVak
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    suspend fun insertInHistory(studieVak: StudieVak) {
        withContext(Dispatchers.IO) {
            stats.insert(StudieVakHistory(studieVak.name, 0, 0L))
        }
    }

    suspend fun getFromHistory(vakNaam: String): StudieVakHistory {
        return withContext(Dispatchers.IO) {
            stats.getVak(vakNaam)
        }
    }
}