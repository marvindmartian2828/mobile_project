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

@Composable
fun ProfileScreen(navController: NavController) { // ✅ Removed `onLogout`
    var name by remember { mutableStateOf(TextFieldValue("John Doe")) }
    var email by remember { mutableStateOf(TextFieldValue("johndoe@email.com")) }
    var phone by remember { mutableStateOf(TextFieldValue("123-456-7890")) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Profile",
            fontSize = 20.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Editable Profile Fields
        ProfileTextField(label = "Full Name", value = name, onValueChange = { name = it })
        ProfileTextField(label = "Email", value = email, onValueChange = { email = it })
        ProfileTextField(label = "Phone", value = phone, onValueChange = { phone = it })

        Spacer(modifier = Modifier.height(16.dp))

        // Toggle Notification Switch
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Enable Notifications",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                    uncheckedThumbColor = Color.Gray
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Change Password Button (UI Only)
        Button(
            onClick = { /* Change Password Logic */ },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Password", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f)) // Push bottom navigation down

        // ✅ Bottom Navigation Menu
        BottomNavigationMenu(navController)
    }
}

@Composable
fun ProfileTextField(label: String, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textStyle = LocalTextStyle.current.copy(color = Color.White)
    )
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController() // ✅ Create a mock NavController
    ProfileScreen(navController = navController) // ✅ Removed `onLogout`
}
