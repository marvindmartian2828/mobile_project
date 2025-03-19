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

data class MaintenanceRecord(val id: Int, var service: String, var date: String, var notes: String)

@Composable
fun MaintenanceRecordsScreen(navController: NavController, carName: String) {
    var records by remember {
        mutableStateOf(
            mutableListOf(
                MaintenanceRecord(1, "Oil Change", "2024-02-10", "Changed engine oil"),
                MaintenanceRecord(2, "Tire Rotation", "2024-01-20", "Rotated all tires")
            )
        )
    }

    var showDialog by remember { mutableStateOf(false) }
    var selectedRecord by remember { mutableStateOf<MaintenanceRecord?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ✅ Header: Car Name
        Text(
            text = "Maintenance Records for $carName",
            fontSize = 20.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Maintenance Records List
        Column(modifier = Modifier.fillMaxWidth()) {
            if (records.isEmpty()) {
                Text(
                    text = "No records added yet.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                records.forEach { record ->
                    MaintenanceItem(
                        record = record,
                        onEdit = { selectedRecord = it; showDialog = true },
                        onDelete = {
                            records = records.filter { it.id != record.id }.toMutableList()
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Add Record Button
        Button(
            onClick = { selectedRecord = null; showDialog = true },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add New Record", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f)) // Pushes bottom navigation down

        // ✅ Bottom Navigation Menu
        BottomNavigationMenu(navController)
    }

    // ✅ Show Add/Edit Record Dialog
    if (showDialog) {
        MaintenanceDialog(
            record = selectedRecord,
            onDismiss = { showDialog = false },
            onSave = { newRecord ->
                if (selectedRecord == null) {
                    records = (records + newRecord.copy(id = (records.maxOfOrNull { it.id } ?: 0) + 1)).toMutableList()
                } else {
                    records = records.map { if (it.id == selectedRecord?.id) newRecord else it }.toMutableList()
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun MaintenanceItem(record: MaintenanceRecord, onEdit: (MaintenanceRecord) -> Unit, onDelete: (MaintenanceRecord) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = record.service, color = Color.White, fontSize = 18.sp)
            Text(text = "Date: ${record.date}", color = Color.Gray, fontSize = 14.sp)
            Text(text = record.notes, color = Color.LightGray, fontSize = 12.sp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { onEdit(record) }) {
                    Text("Edit", color = Color.Yellow)
                }
                TextButton(onClick = { onDelete(record) }) {
                    Text("Delete", color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun MaintenanceDialog(record: MaintenanceRecord?, onDismiss: () -> Unit, onSave: (MaintenanceRecord) -> Unit) {
    var service by remember { mutableStateOf(TextFieldValue(record?.service ?: "")) }
    var date by remember { mutableStateOf(TextFieldValue(record?.date ?: "")) }
    var notes by remember { mutableStateOf(TextFieldValue(record?.notes ?: "")) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(if (record == null) "Add Record" else "Edit Record", color = Color.White) },
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
                    onSave(MaintenanceRecord(record?.id ?: 0, service.text, date.text, notes.text))
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
fun MaintenanceRecordsPreview() {
    val navController = rememberNavController()
    MaintenanceRecordsScreen(navController = navController, carName = "Ford Bronco Sport")
}
