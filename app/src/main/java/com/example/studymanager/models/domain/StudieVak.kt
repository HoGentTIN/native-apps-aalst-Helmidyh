package com.example.studymanager.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "studie_vak_table")
data class StudieVak(
    @PrimaryKey(autoGenerate = true)
    var studieVakId: Int = 0,
    @ColumnInfo(name = "studie_vak_title")
    var name: String
)
//voorlopig nog enkel deze 2, eerst basis functionaliteit tot en met recyclerview af hebben