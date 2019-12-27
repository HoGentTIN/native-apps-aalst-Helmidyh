package com.example.studymanager.models.DTO

import com.example.studymanager.domain.StudieTask

data class StudieTaskDTO(
    var studyTaskId: Int,
    val studyTaskTitle: String,
    val totalTaskDuration: Long,
    var remainingTaskTime: Long,
    var vakId: Int,
    var vakName: String,
    var gebruikerId: Int

) {

    fun toModel(): StudieTask {
        return StudieTask(
            studyTaskId = studyTaskId,
            studyTaskTitle = studyTaskTitle,
            totalTaskDuration = totalTaskDuration,
            remainingTaskTime = remainingTaskTime,
            vakId = vakId,
            vakName = vakName
        )
    }
}