package com.example.studymanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.studymanager.domain.StudieTask

@Dao
interface StudieTaskDAO {

    @Insert
    fun insert(task: StudieTask)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tasks: StudieTask)

    @Query("DELETE FROM studie_task_table")
    fun clear(): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: StudieTask): Int

    @Query("SELECT * from studie_task_table WHERE studyTaskId = :key")
    fun get(key: Int): StudieTask

    @Delete
    fun delete(task: StudieTask)

    @Query("SELECT * from studie_task_table ORDER BY studyTaskId DESC")
    fun getAllTasks():LiveData<List<StudieTask>>

    @Query("SELECT * from studie_task_table ORDER BY studyTaskId DESC LIMIT 1")
    fun getMostRecentTask():StudieTask?

    @Query("SELECT * from studie_task_table WHERE vakId = :vak ")
    fun getAllTasksForVak(vak: Int):LiveData<List<StudieTask>>
}