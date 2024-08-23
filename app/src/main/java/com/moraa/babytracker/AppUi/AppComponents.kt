package com.moraa.babytracker.AppUi

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.moraa.babytracker.AppDatabase.SleepEntry

@Composable
fun AddSleepEntryDialog(onDismiss: () -> Unit, onSave: (SleepEntry) -> Unit) {
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Sleep Entry") },
        text = {
            Column {
                TextField(value = startTime, onValueChange = { startTime = it }, label = { Text("Start Time") })
                TextField(value = endTime, onValueChange = { endTime = it }, label = { Text("End Time") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val entry = SleepEntry(startTime = parseTime(startTime), endTime = parseTime(endTime))
                onSave(entry)
                onDismiss()
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// Function to parse time input
fun parseTime(time: String): Long {
    // Implement parsing logic (e.g., convert to milliseconds)
    return System.currentTimeMillis() // Placeholder
}
@Composable
fun HomeScreen(navController: NavController) {
    Column {
        Text(text = "Recent Activities")
        // Show summary of recent sleep, eating, and diaper change activities
    }
}

