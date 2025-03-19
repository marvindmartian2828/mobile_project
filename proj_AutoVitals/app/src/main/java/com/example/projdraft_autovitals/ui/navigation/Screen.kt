sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object Profile : Screen("profile")
    object CarManagement : Screen("car_management")
    object MaintenanceRecords : Screen("maintenance_records")
    object ServiceCenters : Screen("service_centers")
    object ServiceReminders : Screen("service_reminders")
}
