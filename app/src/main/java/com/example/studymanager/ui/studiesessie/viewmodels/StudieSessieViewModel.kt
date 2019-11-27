package com.example.studymanager.studiesessie

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.*
import com.example.studymanager.database.StudieDatabaseDAO
import com.example.studymanager.domain.StudieTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudieSessieViewModel(private var taskId: Int, private var database: StudieDatabaseDAO, application: Application) :
    AndroidViewModel(application) {

    // STUDIESESSIE OBJECT WORDT GEMAAKT IN CREATIEFRAGMENT, HIER HALEN WE HET ENKEL OP VOOR DE DISPLAY VAN HET OBJECT

    // ALLES REFACTOREN EN OBJECT OPHALEN UIT DB ADHV: taskId


    // hier moet nog een binding komen

    private var _studieTask = MutableLiveData<StudieTask?>()
    private val _timer: CountDownTimer
    private val _timerFinished = MutableLiveData<Boolean>()
    private val _currentTime = MutableLiveData<Long>()
    private var _totalTime = MutableLiveData<Long>()

    val timer: CountDownTimer
        get() = _timer

    val timerFinished: LiveData<Boolean>
        get() = _timerFinished

    val currentTime: LiveData<Long>
        get() = _currentTime

    val totalTime: LiveData<Long>
        get() = _totalTime

    val taskTitle: String =""

    companion object {
        private const val END = 0L
        private const val ONE_SECOND = 1000L
    }

    init {
        viewModelScope.launch {
            getStudieTaskFromDatabase(taskId)
        }
        _studieTask.value!!.studyTaskTitle

        _timer = object : CountDownTimer(totalTime.value!!, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _timerFinished.value = true
                // hier komt dan een melding en een buzzer
            }
        }
    }

    private suspend fun getStudieTaskFromDatabase(taskId: Int): StudieTask? {
        return withContext(Dispatchers.IO) {
            val studie = database.get(taskId)
            studie
        }
    }


    fun onPauze() {
        _totalTime = _currentTime
        timer.cancel()
    }

    fun onResume() {
        when {
            //might never be the same if we are looking at object value and not time value ? idk
            _currentTime != totalTime -> _totalTime = _currentTime
        }
        timer.start()
    }

    fun onTimerFinished() {
        _timerFinished.value = false
        //reset van timer
    }

    fun addTime(amount: String) {
        when (amount) {
            "5" -> _currentTime.value = _currentTime.value?.plus(5000000L)
            "10" -> _currentTime.value = _currentTime.value?.plus(10000000L)
            "15" -> _currentTime.value = _currentTime.value?.plus(15000000L)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}

