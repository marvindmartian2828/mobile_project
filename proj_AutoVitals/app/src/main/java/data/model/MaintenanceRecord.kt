package com.example.projdraft_autovitals.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "maintenance_records",
    foreignKeys = [
        ForeignKey(
            entity = Car::class,
            parentColumns = ["id"],
            childColumns = ["carId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MaintenanceRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val carId: Int, // Foreign key linking to Cars table
    val service: String,
    val serviceDate: String, // Using String for simplicity (can be Date type)
    val notes: String? = null
)
