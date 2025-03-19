package com.example.projdraft_autovitals.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projdraft_autovitals.data.model.User
import com.example.projdraft_autovitals.data.model.AutoVitalsDatabase
import com.example.projdraft_autovitals.data.model.AutoVitalsRepository
import com.example.projdraft_autovitals.ui.viewmodel.AutoVitalsViewModel
import com.example.projdraft_autovitals.ui.viewmodel.AutoVitalsViewModelFactory

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current

    // ✅ Initialize Database & Repository
    val database = AutoVitalsDatabase.getDatabase(context)
    val repository = AutoVitalsRepository(database.autoVitalsDao())

    // ✅ Create ViewModel with Factory
    val factory = AutoVitalsViewModelFactory(repository)
    val viewModel: AutoVitalsViewModel = viewModel(factory = factory)

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ✅ Title
        Text(
            text = "Register",
            fontSize = 28.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Full Name Field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Username Field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.White) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Confirm Password Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password", color = Color.White) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Error Message (if passwords don’t match)
        errorMessage?.let {
            Text(text = it, color = Color.Red, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            // ✅ Register Button (Saves user in Room Database)
            Button(
                onClick = {
                    if (password.text == confirmPassword.text && password.text.isNotEmpty()) {
                        val newUser = User(
                            id = 0, // Auto-generated ID
                            name = name.text,
                            username = username.text,
                            email = email.text,
                            phone = "", // You can add phone input if needed
                            passwordHash = password.text // In production, hash the password
                        )
                        viewModel.insertUser(newUser) // ✅ Save to Room Database
                        navController.navigate(Screen.Dashboard.route) // ✅ Navigate to Dashboard
                    } else {
                        errorMessage = "Passwords do not match or are empty."
                    }
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.weight(1f).padding(end = 4.dp)
            ) {
                Text("Sign Up", color = Color.White, fontSize = 16.sp)
            }

            // ✅ Cancel Button (Navigates back to Welcome Screen)
            OutlinedButton(
                onClick = { navController.navigate(Screen.Welcome.route) }, // ✅ Go back to Welcome Screen
                shape = RoundedCornerShape(50),
                modifier = Modifier.weight(1f).padding(start = 4.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text("Cancel", fontSize = 16.sp)
            }
        }
    }
}

// ✅ Preview
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val dummyNavController = rememberNavController()
    RegisterScreen(navController = dummyNavController) // ✅ Use mock NavController for Preview
}
