package com.example.deadliner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.deadliner.model.Deadline
import com.example.deadliner.model.Subject

const val DATABASE_VERSION = 3
const val DATABASE_NAME = "app_database"

@Database(entities = [Deadline::class, Subject::class], version = DATABASE_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deadlineDao(): DeadlineDao
    abstract fun subjectDao(): SubjectDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            var tempInstance = INSTANCE
            if (tempInstance == null) {
                synchronized(this) {
                    tempInstance = INSTANCE
                    if (tempInstance == null) {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                DATABASE_NAME
                        ).fallbackToDestructiveMigration().build()
                        tempInstance = INSTANCE
                    }
                }
            }
            return tempInstance!!
        }
    }
}