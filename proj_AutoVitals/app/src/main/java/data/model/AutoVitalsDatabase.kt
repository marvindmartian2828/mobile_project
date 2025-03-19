package com.example.projdraft_autovitals.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [User::class, Car::class, MaintenanceRecord::class, ServiceReminder::class],
    version = 1,
    exportSchema = false
)
abstract class AutoVitalsDatabase : RoomDatabase() {
    abstract fun autoVitalsDao(): AutoVitalsDao

    companion object {
        @Volatile
        private var INSTANCE: AutoVitalsDatabase? = null

        fun getDatabase(context: Context): AutoVitalsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AutoVitalsDatabase::class.java,
                    "auto_vitals_database"
                )
                    .fallbackToDestructiveMigration() // ✅ Ensures migration issues don't cause crashes
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
