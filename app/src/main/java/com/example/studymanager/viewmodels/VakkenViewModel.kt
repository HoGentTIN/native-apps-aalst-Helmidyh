package com.example.studymanager.vakken

import android.app.Application
import android.text.InputType
import android.widget.EditText
import androidx.lifecycle.*
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.example.studymanager.database.getDatabase
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.domain.StatsRepository
import com.example.studymanager.models.domain.StudieVakRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VakkenViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * @property database = Database instantie die we altijd dezelfde instantie van application meegeven
     * @property studieVakRepository = Repository voor het bijhouden van vakken, init via abstract type studieVakDao
     * @property _studieVakken = Ophehaalde vakken van repo
     * @property _navigateToVakSessies = Value die aangeeft naar waar welk geklikt vak we moeten navigeren
     */
    private val database = getDatabase(application)
    private val studieVakRepository = StudieVakRepository(database.studieVakDAO, database.statsDAO)

    private var _studieVakken = MutableLiveData<List<StudieVak>>()

    var vakken = studieVakRepository.getAllStudieVakken()

    fun onStudieVakLongClicked(vakId: Int) {
        viewModelScope.launch {
            val clickedVak = studieVakRepository.getStudieVak(vakId)
            delete(clickedVak)
        }
    }

// fun insert(vak: StudieVak) {
//     viewModelScope.launch {
//         studieVakRepository.insert(vak)
//     }
// }

    private fun delete(vak: StudieVak) {
        viewModelScope.launch {
            studieVakRepository.delete(vak)
        }
    }

    /**
     * @property Factory = Maakt instantie's van VakkenViewmodel aan via de meegegeven Application
     */
    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VakkenViewModel::class.java)) {
                return VakkenViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

