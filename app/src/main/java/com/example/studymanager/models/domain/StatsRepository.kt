package com.example.studymanager.models.domain

import com.example.studymanager.database.StatsDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StatsRepository(private val stats: StatsDAO) {

    suspend fun getMeestGestudeerdeVak(): String {
        return withContext(Dispatchers.IO) {
            stats.getMeestGestudeerdeVak()
        }
    }
    suspend fun getMinstGestudeerdeVak(): String {
        return withContext(Dispatchers.IO) {
            stats.geMinstGestudeerdeVak()
        }
    }
    suspend fun getTotaalAantalGestudeerdeUren(): Long{
        return withContext(Dispatchers.IO){
            0L
        }

    }


}