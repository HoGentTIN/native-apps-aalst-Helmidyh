package com.example.studymanager.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_task_table")
data class StudieTask(
    @PrimaryKey(autoGenerate = true)
    var studyTaskId: Int = 0,
    @ColumnInfo(name = "study_task_title")
    val studyTaskTitle: String,
    @ColumnInfo(name = "total_task_duration")
    val totalTaskDuration: Long,
    @ColumnInfo(name = "remaining_task_time")
    var remainingTaskTime: Long
)