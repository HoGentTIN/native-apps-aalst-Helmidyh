package com.example.studymanager.vakken

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.DTO.StudieVakDTO
import com.example.studymanager.models.DTO.StudieVakHistoryDTO
import com.example.studymanager.models.domain.StatsRepository
import com.example.studymanager.models.domain.StudieVakRepository
import kotlinx.coroutines.launch

/**
 * @property studieVakRepository = Repository voor het bijhouden van vakken, init via abstract type studieVakDao
 * @property _studieVakken = Ophehaalde vakken van repo
 */
class VakkenViewModel(
    private val studieVakRepository: StudieVakRepository,
    private val statsRepository: StatsRepository) : ViewModel() {

    private var _studieVakken = MutableLiveData<List<StudieVak>>()

    var vakken = studieVakRepository.getAllStudieVakken()

    fun onStudieVakLongClicked(vakId: Int) {
        viewModelScope.launch {
            val clickedVak = studieVakRepository.getStudieVak(vakId)
            delete(clickedVak)
        }
    }

    fun insert(vak: StudieVak) {
        viewModelScope.launch {
            studieVakRepository.postStudieVak(StudieVakDTO(vak.studieVakId, vak.name, vak.aantalTasks, 0))
            // we schrijven een nieuwe history weg bij creatie van een nieuw vak
            statsRepository.postStats(StudieVakHistoryDTO(0, vak.name, vak.aantalTasks, 0L, 0))
        }
    }

    private fun delete(vak: StudieVak) {
        viewModelScope.launch {
            studieVakRepository.deleteStudieVak(vak.studieVakId)
        }
    }

    /**
     * @property Factory = Maakt instantie's van VakkenViewmodel aan via de meegegeven Application
     */
    class Factory(
        private val studieVakRepository: StudieVakRepository,
        private val statsRepository: StatsRepository
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VakkenViewModel::class.java)) {
                return VakkenViewModel(studieVakRepository, statsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

