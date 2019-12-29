package com.example.studymanager.models.DTO

import com.example.studymanager.domain.StudieTask

data class StudieTaskDTO(
    var studieTaskId: Int,
    val studieTaskTitle: String,
    val totalTaskDuration: Long,
    var remainingTaskTime: Long,
    var vakId: Int,
    var vakName: String,
    var gebruikerId: Int

) {

    fun toModel(): StudieTask {
        return StudieTask(
            studieTaskId = studieTaskId,
            studieTaskTitle = studieTaskTitle,
            totalTaskDuration = totalTaskDuration,
            remainingTaskTime = remainingTaskTime,
            vakId = vakId,
            vakName = vakName
        )
    }
}