package com.example.studymanager.studiesessie

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudieSessieViewModel(private var time: Long, private var taskName: String) : ViewModel() {

    // hier moet nog een binding komen
    private val timer: CountDownTimer
    private val _timerFinished = MutableLiveData<Boolean>()
    private val _currentTime = MutableLiveData<Long>()

    val timerFinished: LiveData<Boolean>
        get() = _timerFinished

    val currentTime: LiveData<Long>
        get() = _currentTime

    val taskTitle: String
        get() = taskName

    companion object {
        private const val END = 0L
        private const val ONE_SECOND = 1000L
    }

    init {
        timer = object : CountDownTimer(time, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)

            }

            override fun onFinish() {
                _timerFinished.value = true
                // hier komt dan een melding en een buzzer
            }
        }
    }

    fun onPauze() {
        time = _currentTime.value!!
        timer.cancel()
    }

    fun onResume() {
        when {
            _currentTime.value!! != time -> time = _currentTime.value!!
        }
        timer.start()
    }

    fun onTimerFinished() {
        _timerFinished.value = false
        //reset van timer
    }

    fun addTime(amount: String) {
        when (amount) {
            "5" -> time += 5000000L
            "10" -> time += 10000000L
            "15" -> time += 15000000L
        }
    }

    override fun onCleared() {
        //garbage collection
        super.onCleared()
        timer.cancel()
    }
}

