package com.moraa.babytracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.moraa.babytracker.AppDatabase.BabyTrackerDatabase
import com.moraa.babytracker.AppDatabase.DiaperEntry
import com.moraa.babytracker.AppDatabase.EatingEntry
import com.moraa.babytracker.AppDatabase.SleepEntry
import kotlinx.coroutines.launch


class BabyTrackerViewModel(application: Application) : AndroidViewModel(application) {
    private val sleepRepository: SleepRepository
    private val eatingRepository: EatingRepository
    private val diaperRepository: DiaperRepository

    val allSleepEntries: LiveData<List<SleepEntry>>
    val allEatingEntries: LiveData<List<EatingEntry>>
    val allDiaperEntries: LiveData<List<DiaperEntry>>

    init {
        val sleepEntryDao = BabyTrackerDatabase.getDatabase(application).sleepEntryDao()
        val eatingEntryDao = BabyTrackerDatabase.getDatabase(application).eatingEntryDao()
        val diaperEntryDao = BabyTrackerDatabase.getDatabase(application).diaperEntryDao()

        sleepRepository = SleepRepository(sleepEntryDao)
        eatingRepository = EatingRepository(eatingEntryDao)
        diaperRepository = DiaperRepository(diaperEntryDao)

        allSleepEntries = sleepRepository.allSleepEntries
        allEatingEntries = eatingRepository.allEatingEntries
        allDiaperEntries = diaperRepository.allDiaperEntries
    }

    // Insert new sleep entry
    fun insertSleepEntry(sleepEntry: SleepEntry) = viewModelScope.launch {
        sleepRepository.insert(sleepEntry)
    }

    // Insert new eating entry
    fun insertEatingEntry(eatingEntry: EatingEntry) = viewModelScope.launch {
        eatingRepository.insert(eatingEntry)
    }

    // Insert new diaper entry
    fun insertDiaperEntry(diaperEntry: DiaperEntry) = viewModelScope.launch {
        diaperRepository.insert(diaperEntry)
    }

    // Calculate total sleep hours
    fun totalSleepHours(): Int {
        return allSleepEntries.value?.sumOf {
            (it.endTime - it.startTime) / (1000 * 60 * 60)
        }?.toInt() ?: 0
    }

    // Calculate total feedings
    fun totalFeedings(): Int {
        return allEatingEntries.value?.size ?: 0
    }

    // Calculate total diaper changes
    fun totalDiapers(): Int {
        return allDiaperEntries.value?.size ?: 0
    }
}
