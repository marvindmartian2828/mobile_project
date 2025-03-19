package com.example.projdraft_autovitals.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import com.example.projdraft_autovitals.ui.components.BottomNavigationMenu
import androidx.navigation.compose.rememberNavController


data class Car(val id: Int, var make: String, var model: String, var year: String, var mileage: String)

@Composable
fun CarManagementScreen(navController: NavController) { // ✅ Pass NavController
    var cars by remember {
        mutableStateOf(
            mutableListOf(
                Car(1, "Ford", "Bronco Sport", "2023", "10,000 km"),
                Car(2, "Porsche", "Cayenne", "2021", "20,000 km")
            )
        )
    }

    var showDialog by remember { mutableStateOf(false) }
    var selectedCar by remember { mutableStateOf<Car?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Manage Your Cars",
            fontSize = 24.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Car List
        Column(modifier = Modifier.fillMaxWidth()) {
            if (cars.isEmpty()) {
                Text(
                    text = "No cars added yet.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                cars.forEach { car ->
                    CarItem(
                        car = car,
                        onEdit = { selectedCar = it; showDialog = true },
                        onDelete = { cars = cars.filter { it.id != car.id }.toMutableList() }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Car Button
        Button(
            onClick = { selectedCar = null; showDialog = true },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add New Car", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f)) // Push bottom navigation down

        // ✅ Bottom Navigation Menu
        BottomNavigationMenu(navController)
    }

    // Show Add/Edit Car Dialog
    if (showDialog) {
        CarDialog(
            car = selectedCar,
            onDismiss = { showDialog = false },
            onSave = { newCar ->
                if (selectedCar == null) {
                    cars.add(newCar.copy(id = cars.size + 1))
                } else {
                    cars = cars.map { if (it.id == selectedCar?.id) newCar else it }.toMutableList()
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun CarItem(car: Car, onEdit: (Car) -> Unit, onDelete: (Car) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${car.make} ${car.model} (${car.year})", color = Color.White, fontSize = 18.sp)
            Text(text = "Mileage: ${car.mileage}", color = Color.Gray)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { onEdit(car) }) {
                    Text("Edit", color = Color.Yellow)
                }
                TextButton(onClick = { onDelete(car) }) {
                    Text("Delete", color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun CarDialog(car: Car?, onDismiss: () -> Unit, onSave: (Car) -> Unit) {
    var make by remember { mutableStateOf(TextFieldValue(car?.make ?: "")) }
    var model by remember { mutableStateOf(TextFieldValue(car?.model ?: "")) }
    var year by remember { mutableStateOf(TextFieldValue(car?.year ?: "")) }
    var mileage by remember { mutableStateOf(TextFieldValue(car?.mileage ?: "")) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(if (car == null) "Add Car" else "Edit Car", color = Color.White) },
        text = {
            Column {
                BasicTextField(
                    value = make,
                    onValueChange = { make = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray, RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                BasicTextField(
                    value = model,
                    onValueChange = { model = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray, RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                BasicTextField(
                    value = year,
                    onValueChange = { year = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray, RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                BasicTextField(
                    value = mileage,
                    onValueChange = { mileage = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray, RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(Car(car?.id ?: 0, make.text, model.text, year.text, mileage.text))
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
fun CarManagementPreview() {
    val navController = rememberNavController() // ✅ Fix: Use a valid NavController
    CarManagementScreen(navController = navController)
}
