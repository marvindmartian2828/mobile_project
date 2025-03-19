package com.example.projdraft_autovitals.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projdraft_autovitals.ui.components.BottomNavigationMenu

data class ServiceReminder(val id: Int, var service: String, var date: String, var notes: String)

@Composable
fun ServiceRemindersScreen(navController: NavController) { // ✅ Accept NavController
    var reminders by remember {
        mutableStateOf(
            mutableListOf(
                ServiceReminder(1, "Oil Change", "2024-03-15", "Full synthetic oil change"),
                ServiceReminder(2, "Tire Rotation", "2024-04-01", "Rotate tires and check pressure")
            )
        )
    }

    var showDialog by remember { mutableStateOf(false) }
    var selectedReminder by remember { mutableStateOf<ServiceReminder?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Service Reminders",
            fontSize = 20.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Reminder List
        Column(modifier = Modifier.fillMaxWidth()) {
            if (reminders.isEmpty()) {
                Text(
                    text = "No reminders added yet.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                reminders.forEach { reminder ->
                    ReminderItem(
                        reminder = reminder,
                        onEdit = { selectedReminder = it; showDialog = true },
                        onDelete = { reminders = reminders.filter { it.id != reminder.id }.toMutableList() }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Reminder Button
        Button(
            onClick = { selectedReminder = null; showDialog = true },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add New Reminder", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f)) // Push Bottom Navigation to bottom

        // ✅ Bottom Navigation Menu
        BottomNavigationMenu(navController)
    }

    // Show Add/Edit Reminder Dialog
    if (showDialog) {
        ReminderDialog(
            reminder = selectedReminder,
            onDismiss = { showDialog = false },
            onSave = { newReminder ->
                if (selectedReminder == null) {
                    reminders.add(newReminder.copy(id = reminders.size + 1))
                } else {
                    reminders = reminders.map { if (it.id == selectedReminder?.id) newReminder else it }.toMutableList()
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun ReminderItem(reminder: ServiceReminder, onEdit: (ServiceReminder) -> Unit, onDelete: (ServiceReminder) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = reminder.service, color = Color.White, fontSize = 18.sp)
            Text(text = "Date: ${reminder.date}", color = Color.Gray, fontSize = 14.sp)
            Text(text = reminder.notes, color = Color.LightGray, fontSize = 12.sp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { onEdit(reminder) }) {
                    Text("Edit", color = Color.Yellow)
                }
                TextButton(onClick = { onDelete(reminder) }) {
                    Text("Delete", color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun ReminderDialog(reminder: ServiceReminder?, onDismiss: () -> Unit, onSave: (ServiceReminder) -> Unit) {
    var service by remember { mutableStateOf(TextFieldValue(reminder?.service ?: "")) }
    var date by remember { mutableStateOf(TextFieldValue(reminder?.date ?: "")) }
    var notes by remember { mutableStateOf(TextFieldValue(reminder?.notes ?: "")) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(if (reminder == null) "Add Reminder" else "Edit Reminder", color = Color.White) },
        text = {
            Column {
                OutlinedTextField(
                    value = service,
                    onValueChange = { service = it },
                    label = { Text("Service", color = Color.White) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (YYYY-MM-DD)", color = Color.White) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes", color = Color.White) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(ServiceReminder(reminder?.id ?: 0, service.text, date.text, notes.text))
                }
            ) {
                Text("Save", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Gray)
            }
        },
        containerColor = Color.Black
    )
}

@Preview(showBackground = true)
@Composable
fun ServiceRemindersPreview() {
    val navController = rememberNavController() // ✅ Create a mock NavController for preview
    ServiceRemindersScreen(navController = navController)
}
