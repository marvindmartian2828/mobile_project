package com.example.projdraft_autovitals.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigationMenu(navController: NavController) {
    val tabs = listOf(
        Screen.Dashboard.route to Icons.Filled.Home,
        Screen.Profile.route to Icons.Filled.Person,
        "logout" to Icons.AutoMirrored.Filled.ExitToApp
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.DarkGray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        tabs.forEach { (route, icon) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = {
                    if (route == "logout") {
                        // ✅ Logout: Navigate to Welcome and clear back stack
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    } else {
                        // ✅ Navigate normally between Dashboard & Profile
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = route,
                        tint = if (currentRoute == route) Color(0xFF4CAF50) else Color.White
                    )
                },
                label = {
                    Text(
                        text = when (route) {
                            Screen.Dashboard.route -> "Dashboard"
                            Screen.Profile.route -> "Profile"
                            "logout" -> "Logout"
                            else -> "Unknown"
                        },
                        color = if (currentRoute == route) Color(0xFF4CAF50) else Color.White,
                        fontSize = 12.sp
                    )
                }
            )
        }
    }
}

// ✅ FIXED: Corrected Preview Function
@Preview(showBackground = true)
@Composable
fun BottomNavigationMenuPreview() {
    val navController = rememberNavController() // ✅ FIXED: Added a valid NavController
    BottomNavigationMenu(navController)
}
