package com.example.studymanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.studymanager.domain.StudieVak

@Dao
interface StudieVakDAO {

    @Insert
    fun insert(task: StudieVak)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg vakken: StudieVak)

    @Query("DELETE FROM studie_vak_table")
    fun clear(): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: StudieVak): Int

    @Query("SELECT * from studie_vak_table WHERE studieVakId = :key")
    fun get(key: Int): StudieVak

    @Query("SELECT * from studie_vak_table WHERE name = :vakName")
    fun get(vakName: String): StudieVak

    @Delete
    fun delete(task: StudieVak)

    @Query("SELECT * from studie_vak_table ORDER BY studieVakId DESC")
    fun getAllVakken(): LiveData<List<StudieVak>>

}
