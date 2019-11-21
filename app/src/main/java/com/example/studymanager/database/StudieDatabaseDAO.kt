package com.example.studymanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieVak

@Dao
interface StudieDatabaseDAO {

    @Insert
    fun insert(task: StudieTask)

    @Update
    fun update(task: StudieTask)

    @Query("SELECT * from study_task_table WHERE studyTaskId = :key")
    fun get(key: Int): StudieTask

    @Delete
    fun delete(task: StudieTask)

    @Query("SELECT * from study_task_table ORDER BY studyTaskId DESC")
    fun getAllTasks():List<StudieTask>

    @Query("SELECT * from study_task_table ORDER BY studyTaskId DESC LIMIT 1")
    fun getMostRecentTask():StudieTask?

    @Query("SELECT * from study_task_table WHERE studie_vak = :vak ")
    fun getAllTasksForVak(vak: String):List<StudieTask>
}