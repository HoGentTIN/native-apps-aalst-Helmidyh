package com.example.studymanager.models.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studie_vak_history")
data class StudieVakHistory(
    @PrimaryKey
    var studyVakHistoryName: String,
    var aantalTasks: Int,
    var totaleStudieTijd: Long
)