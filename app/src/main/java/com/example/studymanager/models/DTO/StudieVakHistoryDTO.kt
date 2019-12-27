package com.example.studymanager.models.DTO

import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.domain.StudieVakHistory

data class StudieVakHistoryDTO(
    var studyVakHistoryId: Int,
    var studyVakHistoryName: String,
    var aantalTasks: Int,
    var totaleStudieTijd: Long,
    var gebruikerId: Int

) {

    fun toModel(): StudieVakHistory {
        return StudieVakHistory(
            studyVakHistoryId = studyVakHistoryId,
            studyVakHistoryName = studyVakHistoryName,
            aantalTasks = aantalTasks,
            totaleStudieTijd = totaleStudieTijd
        )
    }
}