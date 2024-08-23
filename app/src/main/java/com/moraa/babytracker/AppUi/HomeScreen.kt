package com.moraa.babytracker.AppUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Button
import com.moraa.babytracker.BabyTrackerViewModel
import com.moraa.babytracker.SleepViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.navigation.NavController
import com.moraa.babytracker.AppDatabase.DiaperEntry
import com.moraa.babytracker.AppDatabase.EatingEntry
import com.moraa.babytracker.AppDatabase.SleepEntry
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

/*@Composable
fun BabyTrackerApp() {
    val navController = rememberNavController()
    @Composable
    fun BabyTrackerApp() {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "home") {
            composable("home") { HomeScreen(navController = navController, viewModel = viewModel()) }
            composable("sleep") { SleepScreen(viewModel = viewModel()) }
            composable("diaper") { DiaperScreen(viewModel = viewModel()) } // Add this line
            // Add other screens similarly
        }
    }

}

@Composable
fun HomeScreen(navController: NavController, viewModel: BabyTrackerViewModel) {
    val totalSleep = viewModel.totalSleepHours()
    val totalFeedings = viewModel.totalFeedings()
    val totalDiapers = viewModel.totalDiapers()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Baby Tracker", style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(16.dp))

        // Summary Cards
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            SummaryCard(title = "Sleep", count = totalSleep)
            SummaryCard(title = "Feedings", count = totalFeedings)
            SummaryCard(title = "Diapers", count = totalDiapers)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation buttons
        androidx.compose.material3.Button(onClick = { navController.navigate("sleep") }) {
            Text("Track Sleep")
        }
        androidx.compose.material3.Button(onClick = { navController.navigate("eating") }) {
            Text("Track Feeding")
        }
        androidx.compose.material3.Button(onClick = { navController.navigate("diaper") }) {
            Text("Track Diaper Change")
        }
    }
}*/

@Composable
fun SummaryCard(title: String, count: Int) {
    Card(
        modifier = Modifier

            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.h6)
            Text(text = count.toString(), style = MaterialTheme.typography.h4)
        }
    }
}

@Composable
fun SleepScreen(viewModel: BabyTrackerViewModel = viewModel()) {
    val sleepEntries by viewModel.allSleepEntries.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add Sleep Entry") },
            text = {
                Column {
                    TextField(
                        value = startTime,
                        onValueChange = { startTime = it },
                        label = { Text("Start Time (HH:MM)") }
                    )
                    TextField(
                        value = endTime,
                        onValueChange = { endTime = it },
                        label = { Text("End Time (HH:MM)") }
                    )
                }
            },
            confirmButton = {
                androidx.compose.material3.Button(
                    onClick = {
                        if (startTime.isNotBlank() && endTime.isNotBlank()) {
                            viewModel.insertSleepEntry(
                                SleepEntry(
                                    startTime = parseTime(startTime),
                                    endTime = parseTime(endTime)
                                )
                            )
                            startTime = ""
                            endTime = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                androidx.compose.material3.Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Sleep Tracker", style = MaterialTheme.typography.h4)

        androidx.compose.material3.Button(onClick = { showDialog = true }) {
            Text("Add Sleep Entry")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display sleep entries
        LazyColumn {
            items(sleepEntries) { entry ->
                val duration = calculateDuration(entry.startTime, entry.endTime)
                Text(
                    text = "Start: ${formatTime(entry.startTime)} | End: ${formatTime(entry.endTime)} | Duration: ${formatDuration(duration)}",
                    style = MaterialTheme.typography.body1
                )
            }
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




@Composable
fun DiaperScreen(viewModel: BabyTrackerViewModel = viewModel()) {
    val diaperEntries by viewModel.allDiaperEntries.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var diaperType by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add Diaper Entry") },
            text = {
                Column {
                    TextField(
                        value = diaperType,
                        onValueChange = { diaperType = it },
                        label = { Text("Diaper Type (e.g., Wet, Dirty)") }
                    )
                }
            },
            confirmButton = {
                androidx.compose.material3.Button(
                    onClick = {
                        if (diaperType.isNotBlank()) {
                            viewModel.insertDiaperEntry(DiaperEntry( type = diaperType))
                            diaperType = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                androidx.compose.material3.Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Diaper Tracker", style = MaterialTheme.typography.h4)

        androidx.compose.material3.Button(onClick = { showDialog = true }) {
            Text("Add Diaper Entry")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display diaper entries
        LazyColumn {
            items(diaperEntries) { entry ->
                Text(
                    text = "Type: ${entry.type} at ${formatTime(entry.time)}",
                    style = MaterialTheme.typography.body1
                )
            }
        }

    }
    }


@Composable
fun EatScreen(viewModel: BabyTrackerViewModel = viewModel()) {
    val eatingEntries by viewModel.allEatingEntries.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var foodType by remember { mutableStateOf("") }
    var feedingTime by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add Eating Entry") },
            text = {
                Column {
                    TextField(
                        value = foodType,
                        onValueChange = { foodType = it },
                        label = { Text("Food Type") }
                    )
                    TextField(
                        value = feedingTime,
                        onValueChange = { feedingTime = it },
                        label = { Text("Feeding Time (HH:MM)") }
                    )
                }
            },
            confirmButton = {
                androidx.compose.material3.Button(
                    onClick = {
                        if (foodType.isNotBlank() && feedingTime.isNotBlank()) {
                            viewModel.insertEatingEntry(
                                EatingEntry(
                                    type = foodType,
                                    time = parseTime(feedingTime),
                                    amount = ""
                                )
                            )
                            foodType = ""
                            feedingTime = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                androidx.compose.material3.Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Eating Tracker", style = MaterialTheme.typography.h4)

        androidx.compose.material3.Button(onClick = { showDialog = true }) {
            Text("Add Eating Entry")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Summary Card
        //SummaryCard(eatingEntries)

        Spacer(modifier = Modifier.height(16.dp))

        // Display eating entries
        LazyColumn {
            items(eatingEntries) { entry ->
                Text(
                    text = "Food: ${entry.type} at ${formatTime(entry.time)}",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}


// Helper function to format time
fun formatTime(time: Long): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(time)
}



