package com.example.employeeperformancetracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Employee operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee): Long

    @Update
    suspend fun updateEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("SELECT * FROM employees ORDER BY name ASC")
    fun getAllEmployees(): Flow<List<Employee>>

    @Query("SELECT * FROM employees WHERE id = :empId")
    suspend fun getEmployeeById(empId: Int): Employee?

    // Task operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY deadline ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE employeeId = :empId")
    fun getTasksForEmployee(empId: Int): Flow<List<Task>>

    // Performance operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerformance(performance: Performance): Long

    @Update
    suspend fun updatePerformance(performance: Performance)

    @Delete
    suspend fun deletePerformance(performance: Performance)

    @Query("SELECT * FROM performance ORDER BY date DESC")
    fun getAllPerformances(): Flow<List<Performance>>

    @Query("SELECT * FROM performance WHERE employeeId = :empId")
    fun getPerformanceForEmployee(empId: Int): Flow<List<Performance>>

    // Attendance operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: Attendance): Long

    @Update
    suspend fun updateAttendance(attendance: Attendance)

    @Query("SELECT * FROM attendance WHERE employeeId = :empId ORDER BY date DESC")
    fun getAttendanceForEmployee(empId: Int): Flow<List<Attendance>>

    @Query("SELECT * FROM attendance WHERE date = :date")
    fun getAttendanceByDate(date: String): Flow<List<Attendance>>
}
