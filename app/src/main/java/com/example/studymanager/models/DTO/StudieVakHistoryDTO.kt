package com.example.studymanager.models.DTO

import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.domain.StudieVakHistory

data class StudieVakHistoryDTO(
    var studieVakHistoryId: Int,
    var studieVakHistoryName: String,
    var aantalTasks: Int,
    var totaleStudieTijd: Long,
    var gebruikerId: Int

) {

    fun toModel(): StudieVakHistory {
        return StudieVakHistory(
            studieVakHistoryId = studieVakHistoryId,
            studieVakHistoryName = studieVakHistoryName,
            aantalTasks = aantalTasks,
            totaleStudieTijd = totaleStudieTijd
        )
    }
}