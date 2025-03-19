package com.example.projdraft_autovitals.data.model

import kotlinx.coroutines.flow.Flow

class AutoVitalsRepository(private val dao: AutoVitalsDao) {

    // ✅ User Operations
    suspend fun insertUser(user: User) = dao.insertUser(user)
    fun getUserById(userId: Int): Flow<User?> = dao.getUserById(userId)

    // ✅ Car Operations
    suspend fun insertCar(car: Car) = dao.insertCar(car)
    fun getCarsByUser(userId: Int): Flow<List<Car>> = dao.getCarsByUser(userId)

    // ✅ Maintenance Record Operations
    suspend fun insertMaintenanceRecord(record: MaintenanceRecord) = dao.insertMaintenanceRecord(record)
    fun getMaintenanceRecordsByCar(carId: Int): Flow<List<MaintenanceRecord>> = dao.getMaintenanceRecordsByCar(carId)

    // ✅ Service Reminder Operations
    suspend fun insertServiceReminder(reminder: ServiceReminder) = dao.insertServiceReminder(reminder)
    fun getServiceRemindersByCar(carId: Int): Flow<List<ServiceReminder>> = dao.getServiceRemindersByCar(carId)
}
