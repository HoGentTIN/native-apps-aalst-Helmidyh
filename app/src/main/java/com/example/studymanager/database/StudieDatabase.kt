package com.example.studymanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieVak
import com.example.studymanager.models.domain.StudieVakHistory


@Database(entities = [StudieTask::class,StudieVak::class,StudieVakHistory::class], version = 4, exportSchema = false)
abstract class StudieDatabase : RoomDatabase() {
    abstract val studieTaskDAO: StudieTaskDAO
    abstract val studieVakDAO: StudieVakDAO
    abstract val statsDAO: StatsDAO
}
//volatile zorgt er voor dat instance altijd up to date is

@Volatile
private lateinit var INSTANCE: StudieDatabase

fun getDatabase(context: Context): StudieDatabase {
    synchronized(StudieDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                StudieDatabase::class.java,
                "studie_history_database"
            ).fallbackToDestructiveMigration().build()
        }
        return INSTANCE
    }
}


