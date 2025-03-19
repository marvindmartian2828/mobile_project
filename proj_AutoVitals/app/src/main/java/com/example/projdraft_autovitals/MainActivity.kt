package com.example.projdraft_autovitals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.projdraft_autovitals.ui.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController() // ✅ Create the NavController
            NavGraph(navController = navController) // ✅ Pass it to NavGraph
        }
    }
}
