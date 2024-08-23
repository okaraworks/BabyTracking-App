package com.moraa.babytracker.AppDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SleepEntryDao {
    @Insert
    suspend fun insert(sleepEntry: SleepEntry)

    @Query("SELECT * FROM sleep_entries ORDER BY startTime DESC")
    fun getAllSleepEntries(): LiveData<List<SleepEntry>>
}

@Dao
interface EatingEntryDao {
    @Insert
    suspend fun insert(eatingEntry: EatingEntry)

    @Query("SELECT * FROM eating_entries ORDER BY time DESC")
    fun getAllEatingEntries(): LiveData<List<EatingEntry>>
}

@Dao
interface DiaperEntryDao {
    @Insert
    suspend fun insert(diaperEntry: DiaperEntry)

    @Query("SELECT * FROM diaper_entries ORDER BY time DESC")
    fun getAllDiaperEntries(): LiveData<List<DiaperEntry>>
}
