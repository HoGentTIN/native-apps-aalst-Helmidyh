package com.example.studymanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @property studieTaskRepository = Repository voor het bijhouden van tasks, init via abstract type studieVakDao
 */
class VakSessiesViewmodel(
    private val vakId: Int,
    private val studieTaskRepository: StudieTaskRepository) : ViewModel() {

    var tasks = studieTaskRepository.getAllStudieTasks()

    fun onStudieTaskLongClicked(taskId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val clickedTask: StudieTask = studieTaskRepository.getStudieTask(taskId)
                delete(clickedTask)
            }
        }
    }

    private fun delete(task: StudieTask) {
        viewModelScope.launch {
            studieTaskRepository.deleteStudieTask(task.studieTaskId)
        }
    }

    class Factory(private val vakId: Int, private val studieTaskRepository: StudieTaskRepository
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VakSessiesViewmodel::class.java)) {
                return VakSessiesViewmodel(vakId, studieTaskRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}