package com.example.studymanager.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studie_task_table")
data class StudieTask(
    @PrimaryKey(autoGenerate = true)
    var studyTaskId: Int = 0,
    val studyTaskTitle: String,
    val totalTaskDuration: Long,
    var remainingTaskTime: Long,
    var vakId: Int,
    var vakName: String
)