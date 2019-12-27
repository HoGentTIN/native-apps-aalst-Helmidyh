/*
package com.example.studymanager.network

import com.example.studymanager.database.DatabaseStudieTask
import com.example.studymanager.domain.StudieTask
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkStudieTaskContainer(val tasks:List<StudieTask>)

//vroeger var studieTaskId: Int = 0
@JsonClass(generateAdapter = true)
data class NetworkStudieTask(
    var studieTaskId: Int,
    val studieTaskTitle: String,
    val totalTaskDuration: Long,
    var remainingTaskTime: Long,
    var vak: String
)

fun NetworkStudieTaskContainer.asDatabaseModel(): Array<DatabaseStudieTask>*/
