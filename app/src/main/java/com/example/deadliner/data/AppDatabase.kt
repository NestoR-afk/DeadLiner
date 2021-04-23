package com.example.deadliner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.deadliner.model.Deadline


const val DATABASE_VERSION = 1
const val DATABASE_NAME = "app_database"

/**
 * App database
 *
 * @constructor Create empty App database
 */
@Database(entities = [Deadline::class], version = DATABASE_VERSION,exportSchema = false)
@TypeConverters(Converters::class)
    abstract class AppDatabase : RoomDatabase() {
    /**
     * Deadline dao
     *
     * @return
     */
    abstract fun deadlineDao(): DeadlineDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Get database
         *
         * @param context
         * @return
         */
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
                        ).build()
                        tempInstance = INSTANCE
                    }
                }
            }
            return tempInstance!!
        }
    }
}