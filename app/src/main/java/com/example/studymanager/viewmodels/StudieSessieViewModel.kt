package com.example.studymanager.studiesessie

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.*
import com.example.studymanager.database.StudieDatabaseDAO
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class StudieSessieViewModel(private var taskId: Int, private var repository: StudieTaskRepository, application: Application) :
    AndroidViewModel(application) {

    private var _studieTask = MutableLiveData<StudieTask?>()
    private lateinit var _taskTimer: CountDownTimer
    private var _taskTimerFinished = MutableLiveData<Boolean>()
    private var _taskCurrentTime = MutableLiveData<Long>()
    private var _taskTotalTime = MutableLiveData<Long>()
    private var _taskTitle = MutableLiveData<String>()

    val taskTimer: CountDownTimer
        get() = _taskTimer

    val taskTimerFinished: LiveData<Boolean>
        get() = _taskTimerFinished

    val taskCurrentTime: LiveData<Long>
        get() = _taskCurrentTime

    val taskTotalTime: LiveData<Long>
        get() = _taskTotalTime

    val taskTitle: LiveData<String>
        get() = _taskTitle

    companion object {
        private const val END = 0L
        private const val ONE_SECOND = 1000L
    }

    init {
        viewModelScope.launch {
            _studieTask.value = getStudieTaskFromDatabase(taskId)
            _taskTotalTime.value = _studieTask?.value?.totalTaskDuration
            _taskCurrentTime.value = _taskTotalTime.value
            _taskTimerFinished.value = _taskCurrentTime.value == END
            _taskTitle.value = _studieTask?.value?.studyTaskTitle

            _taskTimer = object : CountDownTimer(taskTotalTime.value!!, ONE_SECOND) {
                override fun onTick(millisUntilFinished: Long) {
                    _taskCurrentTime.value = (millisUntilFinished / ONE_SECOND)
                }

                override fun onFinish() {
                    _taskTimerFinished.value = true
                    // hier komt dan een melding en een buzzer
                }
            }

        }
    }

    private suspend fun getStudieTaskFromDatabase(taskId: Int): StudieTask? {
        return withContext(Dispatchers.IO) {
            val studie = repository.getStudieTask(taskId)
            studie
        }
    }

    fun onPauze() {
        _taskTotalTime.value = _taskCurrentTime.value
        taskTimer.cancel()
    }

    fun onResume() {
        if (taskCurrentTime.value != taskTotalTime.value) {
            _taskTotalTime.value = _taskCurrentTime.value
        }
        taskTimer.start()
    }

    fun onTimerFinished() {
        _taskTimerFinished.value = false
        //reset van timer
    }

    fun addTime(amount: String) {
        when (amount) {
            "5" -> _taskCurrentTime.value = _taskCurrentTime.value?.plus(300000L)
            "10" -> _taskCurrentTime.value = _taskCurrentTime.value?.plus(600000L)
            "15" -> _taskCurrentTime.value = _taskCurrentTime.value?.plus(900000L)
        }
    }

    override fun onCleared() {
        super.onCleared()
        taskTimer.cancel()
    }

    private fun timeFormatter(time: Long?): String {
        return String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(time!!),
            TimeUnit.MILLISECONDS.toMinutes(time) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
            TimeUnit.MILLISECONDS.toSeconds(time) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );
    }
}

