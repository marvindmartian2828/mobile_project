package com.example.projdraft_autovitals.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projdraft_autovitals.data.model.*
import kotlinx.coroutines.launch

class AutoVitalsViewModel(private val repository: AutoVitalsRepository) : ViewModel() {

    // ✅ Insert Operations
    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun insertCar(car: Car) {
        viewModelScope.launch {
            repository.insertCar(car)
        }
    }

    fun insertMaintenanceRecord(record: MaintenanceRecord) {
        viewModelScope.launch {
            repository.insertMaintenanceRecord(record)
        }
    }

    fun insertServiceReminder(reminder: ServiceReminder) {
        viewModelScope.launch {
            repository.insertServiceReminder(reminder)
        }
    }
}
