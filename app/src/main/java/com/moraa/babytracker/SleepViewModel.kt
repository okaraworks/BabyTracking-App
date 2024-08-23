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

class SleepViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SleepRepository
    val allSleepEntries: LiveData<List<SleepEntry>>

    init {
        val sleepEntryDao = BabyTrackerDatabase.getDatabase(application).sleepEntryDao()
        repository = SleepRepository(sleepEntryDao)
        allSleepEntries = repository.allSleepEntries
    }

    fun insert(sleepEntry: SleepEntry) = viewModelScope.launch {
        repository.insert(sleepEntry)
    }
}
class EatingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EatingRepository
    val allEastingEntries: LiveData<List<EatingEntry>>

    init {
        val eatEntryDao = BabyTrackerDatabase.getDatabase(application).eatingEntryDao()
        repository = EatingRepository(eatEntryDao)
        allEastingEntries = repository.allEatingEntries
    }

    fun insert(eatingEntry: EatingEntry) = viewModelScope.launch {
        repository.insert(eatingEntry)
    }
}
class DiaperViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DiaperRepository
    val allDiaperEntries: LiveData<List<DiaperEntry>>

    init {
        val sleepEntryDao = BabyTrackerDatabase.getDatabase(application).diaperEntryDao()
        repository = DiaperRepository(sleepEntryDao)
        allDiaperEntries = repository.allDiaperEntries
    }

    fun insert(diaperEntry: DiaperEntry) = viewModelScope.launch {
        repository.insert(diaperEntry)
    }
}


// Similarly, create EatingViewModel and DiaperViewModel
