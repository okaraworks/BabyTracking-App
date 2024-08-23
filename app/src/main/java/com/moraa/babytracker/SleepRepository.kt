package com.moraa.babytracker

import androidx.lifecycle.LiveData
import com.moraa.babytracker.AppDatabase.DiaperEntry
import com.moraa.babytracker.AppDatabase.DiaperEntryDao
import com.moraa.babytracker.AppDatabase.EatingEntry
import com.moraa.babytracker.AppDatabase.EatingEntryDao
import com.moraa.babytracker.AppDatabase.SleepEntry
import com.moraa.babytracker.AppDatabase.SleepEntryDao

class SleepRepository(private val sleepEntryDao: SleepEntryDao) {
    val allSleepEntries: LiveData<List<SleepEntry>> = sleepEntryDao.getAllSleepEntries()

    suspend fun insert(sleepEntry: SleepEntry) {
        sleepEntryDao.insert(sleepEntry)
    }
}

class EatingRepository(private val eatingEntryDao: EatingEntryDao) {
    val allEatingEntries: LiveData<List<EatingEntry>> = eatingEntryDao.getAllEatingEntries()

    suspend fun insert(eatingEntry: EatingEntry) {
        eatingEntryDao.insert(eatingEntry)
    }
}

class DiaperRepository(private val diaperEntryDao: DiaperEntryDao) {
    val allDiaperEntries: LiveData<List<DiaperEntry>> = diaperEntryDao.getAllDiaperEntries()

    suspend fun insert(diaperEntry: DiaperEntry) {
        diaperEntryDao.insert(diaperEntry)
    }
}
