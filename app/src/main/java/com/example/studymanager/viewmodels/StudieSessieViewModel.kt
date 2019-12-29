package com.example.studymanager.studiesessie

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.*
import com.example.studymanager.database.getDatabase
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import com.example.studymanager.models.DTO.StudieTaskDTO
import kotlinx.coroutines.*

class StudieSessieViewModel(private var taskId: Int, application: Application) :
    AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val studieTaskRepository = StudieTaskRepository(database.studieTaskDAO,database.statsDAO)

    private var _studieTask = MutableLiveData<StudieTask>()
    private var _taskTimer: CountDownTimer? = null
    private var _taskTimerFinished = MutableLiveData<Boolean>()
    private var _taskTotalTime = MutableLiveData<Long>()
    private val _taskTitle: LiveData<String>
        get() = Transformations.map(_studieTask) { x -> x.studieTaskTitle }


    val taskTimerFinished: LiveData<Boolean>
        get() = _taskTimerFinished

    val taskTitle: LiveData<String>
        get() = _taskTitle

    val currentTimeString = Transformations.map(_studieTask) { task ->
        DateUtils.formatElapsedTime(task.remainingTaskTime / ONE_SECOND)
    }

    companion object {
        private const val END = 0L
        private const val ONE_SECOND = 1000L
    }

    init {
        viewModelScope.launch {
            _studieTask.value = getStudieTaskFromDatabase(taskId).await()
            val timeRemaining = _studieTask.value!!.remainingTaskTime

            _taskTotalTime.value = _studieTask.value?.totalTaskDuration
            _taskTimerFinished.value = timeRemaining == END
            createCountDownTimer(timeRemaining)

        }
    }

    private fun getStudieTaskFromDatabase(taskId: Int): Deferred<StudieTask?> {
        return viewModelScope.async(Dispatchers.IO) {
            val studie = studieTaskRepository.getStudieTask(taskId)
            studie
        }
    }

    fun onPauze() {
        _taskTimer?.cancel()
    }

    fun onResume() {
        _taskTimer?.start()
    }

    fun onTimerFinished() {
        _taskTimerFinished.value = false
        // delete task or add more time ?
    }

    fun addTime(amount: String) {
        _taskTimer?.cancel()

        val timeRemaining = _studieTask.value!!.remainingTaskTime

        when (amount) {
            "5" -> createCountDownTimer(timeRemaining + 300000L)
            "10" -> createCountDownTimer(timeRemaining + 600000L)
            "15" -> createCountDownTimer(timeRemaining + 900000L)
        }

        _taskTimer?.start()
    }

    private fun createCountDownTimer(time: Long) {
        _taskTimer = object : CountDownTimer(time, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _studieTask.value!!.remainingTaskTime = millisUntilFinished

                // Manueel: Send update naar LiveData!
                // Een property update, update de LiveData zelf niet, dus geen observer call
                _studieTask.value = _studieTask.value
                updateChanges()
            }

            override fun onFinish() {
                _taskTimerFinished.value = true
                // hier komt dan een melding en een buzzer
            }

        }
    }

    fun updateChanges() {
        viewModelScope.launch {
            studieTaskRepository.update(_studieTask.value!!)
        }
    }

    fun persistTime(){
        viewModelScope.launch {
            var x = _studieTask.value!!
            studieTaskRepository.putStudieTask(StudieTaskDTO(x.studieTaskId,x.studieTaskTitle,x.totalTaskDuration,x.remainingTaskTime,x.vakId,x.vakName,0))
        }
    }

    class Factory(private val taskId: Int, private val application: Application) :
        ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StudieSessieViewModel::class.java)) {
                return StudieSessieViewModel(taskId, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

