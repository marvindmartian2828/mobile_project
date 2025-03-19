package com.example.projdraft_autovitals.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projdraft_autovitals.ui.components.BottomNavigationMenu
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

data class ServiceCenter(
    val id: Int,
    val name: String,
    val latLng: LatLng
)

@Composable
fun ServiceCentersScreen(navController: NavController) { // ✅ Accept NavController
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var isPermissionGranted by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var filteredCenters by remember { mutableStateOf<List<ServiceCenter>>(listOf()) }

    // Request Location Permission
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            isPermissionGranted = granted
            if (granted) {
                getUserLocation(fusedLocationClient) { location ->
                    userLocation = LatLng(location.latitude, location.longitude)
                }
            } else {
                Toast.makeText(context, "Location Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // Check & Request Permission
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            isPermissionGranted = true
            getUserLocation(fusedLocationClient) { location ->
                userLocation = LatLng(location.latitude, location.longitude)
            }
        }
    }

    val allServiceCenters = listOf(
        ServiceCenter(1, "Express Mechanics", LatLng(51.0447, -114.0719)), // Downtown Calgary
        ServiceCenter(2, "AutoFix Calgary", LatLng(51.0486, -114.0708)), // Calgary Tower Area
        ServiceCenter(3, "Calgary Auto Care", LatLng(51.0522, -114.0680)), // Beltline
        ServiceCenter(4, "Speedy Tire & Auto", LatLng(51.0500, -114.0850)), // Sunalta
        ServiceCenter(5, "Pro Mechanic Calgary", LatLng(51.0391, -114.0713)) // Mission District
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation ?: LatLng(51.0447, -114.0719), 10f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Nearby Service Centers",
            fontSize = 20.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Search Bar & Search Button
        Row(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    filteredCenters = if (searchQuery.text.isNotEmpty()) {
                        allServiceCenters.filter {
                            it.name.contains(searchQuery.text, ignoreCase = true)
                        }
                    } else {
                        allServiceCenters
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Search", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Refresh Location Button
        Button(
            onClick = {
                getUserLocation(fusedLocationClient) { location ->
                    userLocation = LatLng(location.latitude, location.longitude)
                }
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Refresh Location", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Google Maps
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Gray)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = isPermissionGranted),
                uiSettings = MapUiSettings(zoomControlsEnabled = true)
            ) {
                userLocation?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = "Your Location",
                        snippet = "You are here"
                    )
                    cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(it, 12f))
                }

                filteredCenters.forEach { center ->
                    Marker(
                        state = MarkerState(position = center.latLng),
                        title = center.name,
                        snippet = "Tap to Navigate",
                        onClick = {
                            val gmmIntentUri =
                                Uri.parse("google.navigation:q=${center.latLng.latitude},${center.latLng.longitude}")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            context.startActivity(mapIntent)
                            true
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Bottom Navigation Menu
        BottomNavigationMenu(navController)
    }
}

@SuppressLint("MissingPermission")
fun getUserLocation(fusedLocationClient: FusedLocationProviderClient, onLocationReceived: (Location) -> Unit) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            onLocationReceived(location)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServiceCentersPreview() {
    val navController = rememberNavController() // ✅ Create a mock NavController
    ServiceCentersScreen(navController = navController)
}
