package com.moraa.babytracker.AppDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SleepEntry::class, EatingEntry::class, DiaperEntry::class], version = 1)
abstract class BabyTrackerDatabase : RoomDatabase() {
    abstract fun sleepEntryDao(): SleepEntryDao
    abstract fun eatingEntryDao(): EatingEntryDao
    abstract fun diaperEntryDao(): DiaperEntryDao

    companion object {
        @Volatile private var instance: BabyTrackerDatabase? = null

        fun getDatabase(context: Context): BabyTrackerDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    BabyTrackerDatabase::class.java,
                    "baby_tracker_database"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}
