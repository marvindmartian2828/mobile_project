package com.example.projdraft_autovitals.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.projdraft_autovitals.ui.screens.*
import com.example.projdraft_autovitals.ui.viewmodel.LoginViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val loginViewModel: LoginViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onSignUpClick = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(navController, loginViewModel)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(navController)
        }

        composable(Screen.ServiceCenters.route) {
            ServiceCentersScreen(navController)
        }

        composable(Screen.CarManagement.route) {
            CarManagementScreen(navController)
        }

        composable(Screen.ServiceReminders.route) {
            ServiceRemindersScreen(navController)
        }

        composable(Screen.MaintenanceRecords.route) {
            MaintenanceRecordsScreen(navController, carName = "Porsche Cayenne 2023")
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
    }
}
