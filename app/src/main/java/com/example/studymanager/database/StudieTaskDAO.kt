package com.example.studymanager.database

import androidx.room.*
import com.example.studymanager.domain.StudieTask

@Dao
interface StudieTaskDAO {

    @Insert
    fun insert(task: StudieTask)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: StudieTask): Int

    @Query("SELECT * from study_task_table WHERE studyTaskId = :key")
    fun get(key: Int): StudieTask

    @Delete
    fun delete(task: StudieTask)

    @Query("SELECT * from study_task_table ORDER BY studyTaskId DESC")
    fun getAllTasks():List<StudieTask>

    @Query("SELECT * from study_task_table ORDER BY studyTaskId DESC LIMIT 1")
    fun getMostRecentTask():StudieTask?

    @Query("SELECT * from study_task_table WHERE vak = :vak ")
    fun getAllTasksForVak(vak: String):List<StudieTask>
}