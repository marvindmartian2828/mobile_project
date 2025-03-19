package com.example.projdraft_autovitals.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projdraft_autovitals.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    val uiState by remember { derivedStateOf { loginViewModel.uiState } } // ✅ Observe UI state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            fontSize = 28.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Username Field
        OutlinedTextField(
            value = uiState.username,
            onValueChange = { loginViewModel.updateUsername(it) }, // ✅ Update ViewModel state
            label = { Text("Username", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Password Field
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { loginViewModel.updatePassword(it) }, // ✅ Update ViewModel state
            label = { Text("Password", color = Color.White) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Show Error Message if Login Fails
        uiState.errorMessage?.let {
            Text(text = it, color = Color.Red, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            // ✅ Login Button (Calls ViewModel's login function)
            Button(
                onClick = { loginViewModel.login { navController.navigate(Screen.Dashboard.route) } },
                enabled = !uiState.isLoading,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.weight(1f).padding(end = 4.dp)
            ) {
                Text(if (uiState.isLoading) "Logging in..." else "Login", color = Color.White, fontSize = 16.sp)
            }

            // ✅ Cancel Button (Navigates back to Welcome Screen)
            OutlinedButton(
                onClick = { navController.navigate(Screen.Welcome.route) },
                shape = RoundedCornerShape(50),
                modifier = Modifier.weight(1f).padding(start = 4.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text("Cancel", fontSize = 16.sp)
            }
        }
    }
}
