package com.example.employeeperformancetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeeperformancetracker.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

enum class UserRole { ADMIN, EMPLOYEE, NONE }

class EptViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).appDao()
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    val employees: StateFlow<List<Employee>> = dao.getAllEmployees()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val tasks: StateFlow<List<Task>> = dao.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val performances: StateFlow<List<Performance>> = dao.getAllPerformances()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _userRole = MutableStateFlow(UserRole.NONE)
    val userRole: StateFlow<UserRole> = _userRole

    private val _currentEmployeeId = MutableStateFlow<Int?>(null)
    val currentEmployeeId: StateFlow<Int?> = _currentEmployeeId

    init {
        // Start syncing all data from Firestore when the ViewModel is created
        syncFromFirestore()
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        val user = auth.currentUser
        if (user != null) {
            viewModelScope.launch {
                // Determine role. If email is admin@gmail.com, it's admin. Otherwise check employee list.
                if (user.email == "admin@gmail.com") {
                    _userRole.value = UserRole.ADMIN
                } else {
                    // Force a Firestore check to be sure we have the latest role info
                    try {
                        val snapshot = firestore.collection("employees")
                            .whereEqualTo("email", user.email)
                            .get().await()
                        
                        val emp = snapshot.toObjects(Employee::class.java).firstOrNull()
                        if (emp != null) {
                            _userRole.value = UserRole.EMPLOYEE
                            _currentEmployeeId.value = emp.id
                            // Ensure it's also in local DB
                            dao.insertEmployee(emp)
                        } else {
                            _userRole.value = UserRole.NONE
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        // Fallback to local check
                        val localEmp = employees.value.find { it.email == user.email }
                        if (localEmp != null) {
                            _userRole.value = UserRole.EMPLOYEE
                            _currentEmployeeId.value = localEmp.id
                        } else {
                            _userRole.value = UserRole.NONE
                        }
                    }
                }
            }
        } else {
            _userRole.value = UserRole.NONE
            _currentEmployeeId.value = null
        }
    }

    // Firebase Auth
    fun signIn(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                checkCurrentUser()
                onSuccess()
            }
            .addOnFailureListener { onError(it.message ?: "Login failed") }
    }

    fun signOut() {
        auth.signOut()
        _userRole.value = UserRole.NONE
        _currentEmployeeId.value = null
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    // Real-time Sync from Firestore to Local Room DB for all entities
    private fun syncFromFirestore() {
        // Sync Employees
        firestore.collection("employees")
            .addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener
                snapshot?.let {
                    val firestoreEmployees = it.toObjects(Employee::class.java)
                    viewModelScope.launch { 
                        firestoreEmployees.forEach { emp -> dao.insertEmployee(emp) }
                        // Re-check current user role in case employee was just added
                        val user = auth.currentUser
                        if (user != null && _userRole.value == UserRole.NONE) {
                            checkCurrentUser()
                        }
                    }
                }
            }

        // Sync Tasks
        firestore.collection("tasks")
            .addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener
                snapshot?.let {
                    val firestoreTasks = it.toObjects(Task::class.java)
                    viewModelScope.launch { firestoreTasks.forEach { task -> dao.insertTask(task) } }
                }
            }

        // Sync Performances
        firestore.collection("performances")
            .addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener
                snapshot?.let {
                    val firestorePerformances = it.toObjects(Performance::class.java)
                    viewModelScope.launch { firestorePerformances.forEach { perf -> dao.insertPerformance(perf) } }
                }
            }

        // Sync Attendance
        firestore.collection("attendance")
            .addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener
                snapshot?.let {
                    val firestoreAttendance = it.toObjects(Attendance::class.java)
                    viewModelScope.launch { firestoreAttendance.forEach { att -> dao.insertAttendance(att) } }
                }
            }
    }

    // Employee management
    fun addEmployee(employee: Employee) {
        viewModelScope.launch { 
            val localId = dao.insertEmployee(employee)
            val employeeWithId = employee.copy(id = localId.toInt())
            try {
                firestore.collection("employees").document(localId.toString()).set(employeeWithId).await()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun updateEmployee(employee: Employee) {
        viewModelScope.launch { 
            dao.updateEmployee(employee)
            try {
                firestore.collection("employees").document(employee.id.toString()).set(employee).await()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun deleteEmployee(employee: Employee) {
        viewModelScope.launch { 
            dao.deleteEmployee(employee)
            try {
                firestore.collection("employees").document(employee.id.toString()).delete().await()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    // Task management
    fun addTask(task: Task) {
        viewModelScope.launch { 
            val localId = dao.insertTask(task)
            val taskWithId = task.copy(id = localId.toInt())
            try {
                firestore.collection("tasks").document(localId.toString()).set(taskWithId).await()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch { 
            dao.updateTask(task)
            try {
                firestore.collection("tasks").document(task.id.toString()).set(task).await()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch { 
            dao.deleteTask(task)
            try {
                firestore.collection("tasks").document(task.id.toString()).delete().await()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    // Performance management
    fun addPerformance(performance: Performance) {
        viewModelScope.launch { 
            val localId = dao.insertPerformance(performance)
            val perfWithId = performance.copy(id = localId.toInt())
            try {
                firestore.collection("performances").document(localId.toString()).set(perfWithId).await()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun updatePerformance(performance: Performance) {
        viewModelScope.launch { 
            dao.updatePerformance(performance)
            try {
                firestore.collection("performances").document(performance.id.toString()).set(performance).await()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun deletePerformance(performance: Performance) {
        viewModelScope.launch { 
            dao.deletePerformance(performance)
            try {
                firestore.collection("performances").document(performance.id.toString()).delete().await()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    // Attendance management
    fun recordAttendance(attendance: Attendance) {
        viewModelScope.launch { 
            val localId = dao.insertAttendance(attendance)
            val attWithId = attendance.copy(id = localId.toInt())
            try {
                firestore.collection("attendance").document(localId.toString()).set(attWithId).await()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun getAttendanceByDate(date: String): StateFlow<List<Attendance>> {
        return dao.getAttendanceByDate(date)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun seedData() {
        viewModelScope.launch {
            if (employees.value.isNotEmpty()) return@launch

            addEmployee(Employee(
                name = "Alice Johnson",
                role = "Senior Developer",
                department = "Engineering",
                joiningDate = "2022-01-15",
                email = "alice.j@example.com",
                contact = "123-456-7890",
                salary = 95000.0
            ))
            addEmployee(Employee(
                name = "Bob Smith",
                role = "Product Manager",
                department = "Product",
                joiningDate = "2021-06-20",
                email = "bob.s@example.com",
                contact = "987-654-3210",
                salary = 105000.0
            ))
        }
    }
}
