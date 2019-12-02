package com.example.studymanager.studiesessie

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.*
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class StudieSessieViewModel(private var taskId: Int, private var repository: StudieTaskRepository, application: Application) :
    AndroidViewModel(application) {

    private var _studieTask = MutableLiveData<StudieTask?>()
    private var _taskTimer: CountDownTimer? = null
    private var _taskTimerFinished = MutableLiveData<Boolean>()
    private val _taskCurrentTime = MutableLiveData<Long>()
    private var _taskTotalTime = MutableLiveData<Long>()
    private var _taskTitle = MutableLiveData<String>()

    val taskTimer: CountDownTimer?
        get() = _taskTimer

    val taskTimerFinished: LiveData<Boolean>
        get() = _taskTimerFinished

    val taskCurrentTime: LiveData<Long>
        get() = _taskCurrentTime


    val taskTotalTime: LiveData<Long>
        get() = _taskTotalTime

    val taskTitle: LiveData<String>
        get() = _taskTitle

    val currentTimeString = Transformations.map(taskCurrentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    companion object {
        private const val END = 0L
        private const val ONE_SECOND = 1000L
    }

    init {
        viewModelScope.launch {
            _studieTask.value = getStudieTaskFromDatabase(taskId).await()
            _taskTotalTime.value = _studieTask.value?.totalTaskDuration
            _taskCurrentTime.value = _taskTotalTime.value
            _taskTimerFinished.value = _taskCurrentTime.value == END
            _taskTitle.value = _studieTask.value?.studyTaskTitle
            createCountDownTimer(_taskTotalTime.value!!)
            _taskCurrentTime.value = (_taskCurrentTime.value!! / ONE_SECOND)

        }
    }

    private fun getStudieTaskFromDatabase(taskId: Int): Deferred<StudieTask?> {
        return viewModelScope.async(Dispatchers.IO) {
            val studie = repository.getStudieTask(taskId)
            studie
        }
    }

    fun onPauze() {
        _taskTimer?.cancel()
    }

    fun onResume() {
        if (_taskCurrentTime.value!! < _taskTotalTime.value!!) {
            _taskTotalTime.value = _taskCurrentTime.value
            createCountDownTimer(_taskTotalTime.value!! * 1000L)
            //1000L zodat we de werkelijke waarde terug omzetten naar ms ipv s
        }
        _taskTimer?.start()
    }

    fun onTimerFinished() {
        _taskTimerFinished.value = false
        // delete task or add more time ?
    }

    fun addTime(amount: String) {
        _taskTotalTime.value = _taskCurrentTime.value
        _taskTimer?.cancel()
        when (amount) {
            "5" -> createCountDownTimer(_taskTotalTime.value!! * 1000L + 300000L)
            "10" -> createCountDownTimer(_taskTotalTime.value!! * 1000L + 600000L)
            "15" -> createCountDownTimer(_taskTotalTime.value!! * 1000L + 900000L)
        }
        _taskTimer?.start()
    }

    fun createCountDownTimer(time: Long) {
        _taskTimer = object : CountDownTimer(time, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _taskCurrentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _taskTimerFinished.value = true
                // hier komt dan een melding en een buzzer
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        taskTimer?.cancel()
    }

}

