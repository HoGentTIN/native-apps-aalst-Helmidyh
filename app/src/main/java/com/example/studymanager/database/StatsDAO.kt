package com.example.studymanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.models.domain.StudieVakHistory

@Dao
interface StatsDAO {

    @Insert
    fun insert(task: StudieVakHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg stats: StudieVakHistory)

    @Query("DELETE FROM studie_vak_history")
    fun clear(): Int

    // update moet wss overriden worden
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: StudieVakHistory): Int

    @Delete
    fun delete(task: StudieTask)

    @Query("Select * from studie_vak_history WHERE studieVakHistoryName = :vak")
    fun getVak(vak: String): StudieVakHistory

    @Query("Select studieVakHistoryName from studie_vak_history WHERE aantalTasks = (select MAX(aantalTasks) from studie_vak_history)")
    fun getMeestGestudeerdeVak(): LiveData<String>

    @Query("Select studieVakHistoryName from studie_vak_history WHERE aantalTasks = (select MIN(aantalTasks) from studie_vak_history)")
    fun geMinstGestudeerdeVak(): LiveData<String>

    @Query("Select SUM(totaleStudieTijd) from studie_vak_history")
    fun getTotaalAantalGestudeerdeUren(): LiveData<Long>
}