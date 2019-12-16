package com.example.studymanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studymanager.domain.StudieTask
import com.example.studymanager.domain.StudieVak


@Database(entities = [StudieTask::class,StudieVak::class], version = 3, exportSchema = false)
abstract class StudieDatabase : RoomDatabase() {
    abstract val studieTaskDAO: StudieTaskDAO
    abstract val studieVakDAO: StudieVakDAO
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


