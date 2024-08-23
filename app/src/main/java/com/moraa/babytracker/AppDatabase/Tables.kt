package com.moraa.babytracker.AppDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_entries")
data class SleepEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startTime: Long,
    val endTime: Long
)

@Entity(tableName = "eating_entries")
data class EatingEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val time: Long,
    val type: String, // Breast milk, formula, solid food, etc.
    val amount: String // Could be volume or description
)

@Entity(tableName = "diaper_entries")
data class DiaperEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val time: Long= System.currentTimeMillis() ,
    val type: String // Wet, dirty, mixed
)
