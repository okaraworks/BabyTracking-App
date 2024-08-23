package com.moraa.babytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moraa.babytracker.AppDatabase.DiaperEntry
import com.moraa.babytracker.AppDatabase.EatingEntry
import com.moraa.babytracker.AppDatabase.SleepEntry
import com.moraa.babytracker.AppUi.DiaperScreen
import com.moraa.babytracker.AppUi.EatScreen
import com.moraa.babytracker.AppUi.SleepScreen
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BabyTrackerApp()
        }
    }
}

@Composable
fun BabyTrackerApp() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "home") {
            composable("home") { HomeScreen(navController = navController, viewModel = viewModel()) }
            composable("sleep") { SleepScreen(viewModel = viewModel()) }
            composable("diaper") { DiaperScreen(viewModel = viewModel()) }
            composable("eat") { EatScreen(viewModel = viewModel()) }

        }
    }
}

@Composable
fun HomeScreen(viewModel: BabyTrackerViewModel = viewModel(), navController: NavController) {
    val sleepEntries by viewModel.allSleepEntries.observeAsState(emptyList())
    val eatingEntries by viewModel.allEatingEntries.observeAsState(emptyList())
    val diaperEntries by viewModel.allDiaperEntries.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Baby Tracker", style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(16.dp))

        // Summary Card
        HomeSummaryCard(sleepEntries, eatingEntries, diaperEntries)

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation Buttons
        Button(onClick = { navController.navigate("sleep") }) {
            Text("Go to Sleep Tracker")
        }
        Button(onClick = { navController.navigate("eat") }) {
            Text("Go to Eating Tracker")
        }
        Button(onClick = { navController.navigate("diaper") }) {
            Text("Go to Diaper Tracker")
        }
    }
}

@Composable
fun HomeSummaryCard(
    sleepEntries: List<SleepEntry>,
    eatingEntries: List<EatingEntry>,
    diaperEntries: List<DiaperEntry>
) {
    // Calculate total sleep duration in milliseconds
    val totalSleepDuration = sleepEntries.sumOf { calculateDuration(it.startTime, it.endTime) }
    val totalEating = eatingEntries.size
    val totalDiapers = diaperEntries.size

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Summary", style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.height(8.dp))

            Text("Total Sleep Duration: ${formatDuration(totalSleepDuration)}")
            Text("Total Eating Entries: $totalEating")
            Text("Total Diaper Changes: $totalDiapers")
        }
    }
}

// Function to calculate duration in milliseconds
fun calculateDuration(startTime: Long, endTime: Long): Long {
    return endTime - startTime
}

// Function to format duration to a user-friendly string
fun formatDuration(duration: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(duration)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60
    return String.format("%02d:%02d hours", hours, minutes)
}




