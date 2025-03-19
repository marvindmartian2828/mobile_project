package com.example.projdraft_autovitals.data.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow  // ✅ Import Flow

@Dao
interface AutoVitalsDao {

    // ✅ Insert Operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: Car)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaintenanceRecord(record: MaintenanceRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServiceReminder(reminder: ServiceReminder)

    // ✅ Fetch Data (Use Flow<> for real-time updates)
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Int): Flow<User?>  // ✅ Changed to Flow<> for real-time updates

    @Query("SELECT * FROM cars WHERE userId = :userId")
    fun getCarsByUser(userId: Int): Flow<List<Car>>  // ✅ Changed to Flow<>

    @Query("SELECT * FROM maintenance_records WHERE carId = :carId")
    fun getMaintenanceRecordsByCar(carId: Int): Flow<List<MaintenanceRecord>>  // ✅ Changed to Flow<>

    @Query("SELECT * FROM service_reminders WHERE carId = :carId")
    fun getServiceRemindersByCar(carId: Int): Flow<List<ServiceReminder>>  // ✅ Changed to Flow<>

    // ✅ Delete Operations (More Efficient)
    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)  // ✅ Delete by ID

    @Query("DELETE FROM cars WHERE id = :carId")
    suspend fun deleteCarById(carId: Int)  // ✅ Delete by ID

    @Query("DELETE FROM maintenance_records WHERE id = :recordId")
    suspend fun deleteMaintenanceRecordById(recordId: Int)  // ✅ Delete by ID

    @Query("DELETE FROM service_reminders WHERE id = :reminderId")
    suspend fun deleteServiceReminderById(reminderId: Int)  // ✅ Delete by ID
}
