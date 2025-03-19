package com.example.projdraft_autovitals.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projdraft_autovitals.ui.components.BottomNavigationMenu
import androidx.navigation.compose.rememberNavController

@Composable
fun DashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(85.dp))

        // ✅ Car Information Section
        CarInfoSection()

        Spacer(modifier = Modifier.height(30.dp))

        // ✅ Navigation Tabs
        NavigationTabs(navController)

        Spacer(modifier = Modifier.height(30.dp))

        // ✅ Car Features
        FeatureCards()

        Spacer(modifier = Modifier.height(30.dp))

        // ✅ Statistics Section
        StatsSection()

        Spacer(modifier = Modifier.height(50.dp))

        // ✅ Navigate to Service Centers
        Button(
            onClick = { navController.navigate(Screen.ServiceCenters.route) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            shape = RoundedCornerShape(50)
        ) {
            Text("Find Nearby Service Centers", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(65.dp))

        // ✅ Fixed Bottom Navigation Bar
        BottomNavigationMenu(navController)
    }
}

// ✅ Car Information
@Composable
fun CarInfoSection() {
    Column {
        Text(
            text = "Porsche Cayenne 2023",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "3.0L Turbo V6, AWD, Automatic",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun NavigationTabs(navController: NavController) {
    val tabs = listOf(
        "Car Details" to Screen.CarManagement.route,
        "Service Reminders" to Screen.ServiceReminders.route
    )
    val longTab = "Maintenance Record"

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(9.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ✅ First row with two buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tabs.forEach { (tabText, route) ->
                Button(
                    onClick = { navController.navigate(route) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = tabText,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // ✅ Second row for "Maintenance Record"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate(Screen.MaintenanceRecords.route) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    text = longTab,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ✅ Feature Cards for Car Components
@Composable
fun FeatureCards() {
    val features = listOf(
        Triple("Car Brakes", "Anatomy", Color.Yellow),
        Triple("Car Fuel Filter", "Last week", Color.Green),
        Triple("Car Indicator", "4 indicators lit", Color.Red),
        Triple("Tire Pressure", "Checked today", Color.Cyan)
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in features.indices step 2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FeatureCard(
                    title = features[i].first,
                    subtitle = features[i].second,
                    textColor = features[i].third,
                    modifier = Modifier.weight(1f)
                )

                if (i + 1 < features.size) {
                    FeatureCard(
                        title = features[i + 1].first,
                        subtitle = features[i + 1].second,
                        textColor = features[i + 1].third,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun FeatureCard(title: String, subtitle: String, textColor: Color, modifier: Modifier) {
    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, color = textColor, fontWeight = FontWeight.Bold)
            Text(text = subtitle, color = Color.White)
        }
    }
}

@Composable
fun StatsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatCard(title = "Battery", value = "50%", subtext = "267 km", modifier = Modifier.weight(1f))
        StatCard(title = "Distance", value = "367 km", subtext = "", modifier = Modifier.weight(1f))
        StatCard(title = "Riding Style", value = "9.72", subtext = "Average Fast", modifier = Modifier.weight(1f))
    }
}

@Composable
fun StatCard(title: String, value: String, subtext: String, modifier: Modifier) {
    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 14.sp, color = Color.White)
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Green)
            if (subtext.isNotEmpty()) {
                Text(text = subtext, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val navController = rememberNavController() //
    DashboardScreen(navController = navController)
}
