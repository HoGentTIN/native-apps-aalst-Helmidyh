package com.example.studymanager.domain

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "studie_task_table", foreignKeys = arrayOf(
        ForeignKey(
            entity = StudieVak::class,
            parentColumns = arrayOf("studieVakId"),
            childColumns = arrayOf("vakId"),
            onDelete = CASCADE
        )
    )
)
data class StudieTask(
    @PrimaryKey(autoGenerate = true)
    var studieTaskId: Int,
    val studieTaskTitle: String,
    val totalTaskDuration: Long,
    var remainingTaskTime: Long,
    var vakId: Int,
    var vakName: String
)