package com.example.studymanager.domain

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "studie_vak_table")
data class StudieVak(
    @PrimaryKey(autoGenerate = true)
    var studieVakId: Int = 0,
    var name: String,
    var aantalTasks: Int = 0
) {
    override fun toString(): String {
        return name
    }
}
