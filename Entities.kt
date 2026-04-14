package com.example.employeeperformancetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val role: String = "",
    val department: String = "",
    val joiningDate: String = "",
    val email: String = "",
    val contact: String = "",
    val imageUrl: String? = null,
    val salary: Double = 0.0
)

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(entity = Employee::class, parentColumns = ["id"], childColumns = ["employeeId"], onDelete = ForeignKey.CASCADE)]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val employeeId: Int = 0,
    val title: String = "",
    val description: String = "",
    val status: String = "Pending", // e.g., "Pending", "In Progress", "Completed"
    val priority: String = "Medium", // e.g., "High", "Medium", "Low"
    val deadline: String = "",
    val completionDate: String? = null,
    val progress: Int = 0 // 0 to 100
)

@Entity(
    tableName = "performance",
    foreignKeys = [ForeignKey(entity = Employee::class, parentColumns = ["id"], childColumns = ["employeeId"], onDelete = ForeignKey.CASCADE)]
)
data class Performance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val employeeId: Int = 0,
    val period: String = "",
    val date: String = "",
    val reviewerName: String = "",
    val qualityScore: Int = 0,
    val timelinessScore: Int = 0,
    val attendanceScore: Int = 0,
    val communicationScore: Int = 0,
    val innovationScore: Int = 0,
    val overallRating: Float = 0f,
    val remarks: String = ""
)

@Entity(
    tableName = "attendance",
    foreignKeys = [ForeignKey(entity = Employee::class, parentColumns = ["id"], childColumns = ["employeeId"], onDelete = ForeignKey.CASCADE)]
)
data class Attendance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val employeeId: Int = 0,
    val date: String = "",
    val status: String = "Present", // "Present", "Absent", "Leave"
    val checkIn: String? = null,
    val checkOut: String? = null
)
