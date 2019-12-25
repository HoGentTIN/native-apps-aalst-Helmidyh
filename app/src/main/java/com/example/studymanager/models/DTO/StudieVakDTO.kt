package com.example.studymanager.models.DTO

import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieVak

data class StudieVakDTO(
    var studieVakId: Int,
    var name: String,
    var aantalTasks: Int,
    var gebruikerId: Int

) {

    fun toModel(): StudieVak {
        return StudieVak(
            studieVakId = studieVakId,
            name = name,
            aantalTasks = aantalTasks
        )
    }
}