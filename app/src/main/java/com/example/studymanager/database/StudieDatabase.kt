package com.example.studymanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studymanager.domain.StudieTask

@Database(entities = [StudieTask::class], version = 1, exportSchema = false)
abstract class StudieDatabase : RoomDatabase() {

    abstract val studieDatabaseDAO: StudieDatabaseDAO

    companion object {
        //volatile zorgt er voor dat instance altijd up to date is
        @Volatile
        private var INSTANCE: StudieDatabase? = null

        fun getInstance(context: Context): StudieDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudieDatabase::class.java,
                        "studie_history_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
//TODO write tests