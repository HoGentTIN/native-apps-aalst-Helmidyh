package com.example.studymanager.database

import androidx.room.*
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.models.domain.StudieVakHistory

@Dao
interface StatsDAO {
    //TODO implement

    @Insert
    fun insert(task: StudieVakHistory)

    // update moet wss overriden worden
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: StudieTask): Int

    // gaat wss niet gebruikt worden, mss nog weglaten
    @Delete
    fun delete(task: StudieTask)

    @Query("Select studyVakHistoryName from studie_vak_history WHERE totaleStudieTijd = (select MAX(totaleStudieTijd) from studie_vak_history)")
    fun getMeestGestudeerdeVak(): String

    @Query("Select studyVakHistoryName from studie_vak_history WHERE totaleStudieTijd = (select MIN(totaleStudieTijd) from studie_vak_history)")
    fun geMinstGestudeerdeVak(): String
}