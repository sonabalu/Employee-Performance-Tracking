package com.example.employeeperformancetracker.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.employeeperformancetracker.data.Attendance
import com.example.employeeperformancetracker.data.Employee
import com.example.employeeperformancetracker.data.Performance
import com.example.employeeperformancetracker.data.Task
import com.example.employeeperformancetracker.ui.theme.*
import com.example.employeeperformancetracker.viewmodel.EptViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished()
    }
    Box(modifier = Modifier.fillMaxSize().background(PrimaryColor), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(100.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("EPT Smart", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text("Employee Performance Tracker", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
        }
    }
}

@Composable
fun LoginScreen(viewModel: EptViewModel, onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(BackgroundColor), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceColor)
        ) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = PrimaryColor, modifier = Modifier.size(64.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Login", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("Sign in to your account", fontSize = 14.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(24.dp))
                
                OutlinedTextField(
                    value = email,
                    onValueChange = { 
                        email = it
                        error = "" 
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    enabled = !isLoading
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        error = "" 
                    },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    enabled = !isLoading
                )
                
                if (error.isNotEmpty()) {
                    Text(error, color = AlertColor, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        val trimmedEmail = email.trim()
                        val trimmedPass = password.trim()
                        if (trimmedEmail.isEmpty() || trimmedPass.isEmpty()) {
                            error = "Please enter credentials"
                        } else {
                            isLoading = true
                            viewModel.signIn(
                                email = trimmedEmail,
                                password = trimmedPass,
                                onSuccess = {
                                    isLoading = false
                                    onLoginSuccess()
                                },
                                onError = {
                                    isLoading = false
                                    error = it
                                }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Sign In", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
                
                TextButton(onClick = { onLoginSuccess() }, modifier = Modifier.padding(top = 8.dp), enabled = !isLoading) {
                    Text("Skip Login (Debug)", color = TextSecondary, fontSize = 12.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: EptViewModel, navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Dashboard", "Employees", "Analytics", "Reports")
    val icons = listOf(Icons.Default.Home, Icons.Default.Person, Icons.Default.Info, Icons.Default.Settings)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EPT Smart Admin", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor),
                actions = {
                    IconButton(onClick = { 
                        viewModel.signOut()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.White)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = SurfaceColor) {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = title) },
                        label = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryColor,
                            selectedTextColor = PrimaryColor,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary,
                            indicatorColor = PrimaryColor.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        },
        containerColor = BackgroundColor
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (selectedTab) {
                0 -> DashboardScreen(viewModel, navController)
                1 -> EmployeeScreen(viewModel, navController)
                2 -> AnalyticsScreen(viewModel)
                3 -> SettingsScreen(viewModel, navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeMainScreen(viewModel: EptViewModel, navController: NavHostController) {
    val currentEmployeeId by viewModel.currentEmployeeId.collectAsState()
    val employees by viewModel.employees.collectAsState()
    val employee = employees.find { it.id == currentEmployeeId }
    
    val tasks by viewModel.tasks.collectAsState()
    val empTasks = tasks.filter { it.employeeId == currentEmployeeId }
    val performances by viewModel.performances.collectAsState()
    val empPerformances = performances.filter { it.employeeId == currentEmployeeId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor),
                actions = {
                    IconButton(onClick = { 
                        viewModel.signOut()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.White)
                    }
                }
            )
        },
        containerColor = BackgroundColor
    ) { paddingValues ->
        if (employee == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Loading your profile...", color = TextSecondary)
                }
            }
        } else {
            Column(modifier = Modifier.padding(paddingValues).fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceColor)) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(modifier = Modifier.size(80.dp).clip(CircleShape).background(PrimaryColor.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                            Text(employee.name.take(1).uppercase(), color = PrimaryColor, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(employee.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(employee.role, color = TextSecondary)
                        Text(employee.department, color = TextSecondary, fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("Mark Attendance", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.recordAttendance(Attendance(employeeId = employee.id, date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()), status = "Present")) }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = AccentColor)) {
                        Text("Present")
                    }
                    OutlinedButton(onClick = { viewModel.recordAttendance(Attendance(employeeId = employee.id, date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()), status = "Absent")) }, modifier = Modifier.weight(1f)) {
                        Text("Absent")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("My Tasks", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                empTasks.forEach { task ->
                    EmployeeTaskItem(task, onUpdateProgress = { newProgress ->
                        val newStatus = if (newProgress == 100) "Completed" else "In Progress"
                        viewModel.updateTask(task.copy(progress = newProgress, status = newStatus))
                    })
                }
                if (empTasks.isEmpty()) Text("No tasks assigned", color = TextSecondary, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(24.dp))
                Text("My Performance Reviews", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                empPerformances.forEach { perf ->
                    PerformanceCard(perf)
                }
                if (empPerformances.isEmpty()) Text("No reviews yet", color = TextSecondary, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun EmployeeTaskItem(task: Task, onUpdateProgress: (Int) -> Unit) {
    var showSlider by remember { mutableStateOf(false) }
    var currentProgress by remember { mutableFloatStateOf(task.progress.toFloat()) }

    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = SurfaceColor)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val statusColor = when(task.status) {
                    "Completed" -> AccentColor
                    "In Progress" -> Color(0xFFFF9800)
                    else -> TextSecondary
                }
                Icon(
                    if (task.status == "Completed") Icons.Default.CheckCircle else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(task.title, fontWeight = FontWeight.Medium)
                    Text("Due: ${task.deadline} • Progress: ${task.progress}%", fontSize = 12.sp, color = TextSecondary)
                }
                IconButton(onClick = { showSlider = !showSlider }) {
                    Icon(if (showSlider) Icons.Default.KeyboardArrowUp else Icons.Default.Edit, contentDescription = "Update Progress")
                }
            }
            
            if (showSlider) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Update Progress: ${currentProgress.toInt()}%", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Slider(
                    value = currentProgress,
                    onValueChange = { currentProgress = it },
                    valueRange = 0f..100f,
                    onValueChangeFinished = { onUpdateProgress(currentProgress.toInt()) }
                )
            }
            
            LinearProgressIndicator(
                progress = { task.progress / 100f },
                modifier = Modifier.fillMaxWidth().height(4.dp).padding(top = 8.dp),
                color = if (task.status == "Completed") AccentColor else PrimaryColor,
                trackColor = Color.LightGray.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun DashboardScreen(viewModel: EptViewModel, navController: NavHostController) {
    val employees by viewModel.employees.collectAsState()
    val tasks by viewModel.tasks.collectAsState()
    val performances by viewModel.performances.collectAsState()

    val completedTasks = tasks.count { it.status == "Completed" || it.status == "Reviewed" }
    val completionRate = if (tasks.isNotEmpty()) (completedTasks * 100) / tasks.size else 0
    val avgRating = if (performances.isNotEmpty()) performances.map { it.overallRating }.average() else 0.0

    Column(modifier = Modifier.padding(16.dp).fillMaxSize().verticalScroll(rememberScrollState())) {
        Text("Quick Stats", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(modifier = Modifier.weight(1f), title = "Employees", value = "${employees.size}", icon = Icons.Default.Person, iconColor = PrimaryColor, bgColor = PrimaryColor.copy(alpha = 0.1f))
            StatCard(modifier = Modifier.weight(1f), title = "Tasks", value = "${tasks.size}", icon = Icons.Default.CheckCircle, iconColor = AccentColor, bgColor = AccentColor.copy(alpha = 0.1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(modifier = Modifier.weight(1f), title = "Avg Rating", value = String.format("%.1f", avgRating), icon = Icons.Default.Star, iconColor = Color(0xFFFF9800), bgColor = Color(0xFFFFF3E0))
            StatCard(modifier = Modifier.weight(1f), title = "Completion", value = "$completionRate%", icon = Icons.Default.Build, iconColor = Color(0xFF9C27B0), bgColor = Color(0xFFF3E5F5))
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Top Performers", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        
        val performers = employees.map { emp ->
            val rating = performances.filter { it.employeeId == emp.id }.map { it.overallRating }.average().let { if (it.isNaN()) 0.0 else it }
            emp to rating
        }.sortedByDescending { it.second }

        val topPerformers = performers.take(3)
        val lowPerformers = performers.filter { it.second > 0 }.reversed().take(3)

        topPerformers.forEach { (emp, rating) ->
            PerformerCard(emp, rating, navController)
        }

        if (lowPerformers.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text("Needs Improvement", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AlertColor)
            Spacer(modifier = Modifier.height(8.dp))
            lowPerformers.forEach { (emp, rating) ->
                PerformerCard(emp, rating, navController)
            }
        }
    }
}

@Composable
fun PerformerCard(emp: Employee, rating: Double, navController: NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { navController.navigate(Screen.EmployeeDetail.createRoute(emp.id)) },
        colors = CardDefaults.cardColors(containerColor = SurfaceColor)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(PrimaryColor.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                Text(emp.name.take(1).uppercase(), color = PrimaryColor, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(emp.name, fontWeight = FontWeight.Bold)
                Text(emp.role, fontSize = 12.sp, color = TextSecondary)
            }
            Text(String.format("%.1f", rating), fontWeight = FontWeight.Bold, color = PrimaryColor)
            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFF9800), modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun StatCard(modifier: Modifier, title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, iconColor: Color, bgColor: Color) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(bgColor), contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = iconColor)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, fontSize = 12.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
                Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            }
        }
    }
}

@Composable
fun EmployeeScreen(viewModel: EptViewModel, navController: NavHostController) {
    val employees by viewModel.employees.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var employeeToEdit by remember { mutableStateOf<Employee?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedDepartment by remember { mutableStateOf("All") }

    val departments = listOf("All") + employees.map { it.department }.distinct()

    val filteredEmployees = employees.filter { 
        (it.name.contains(searchQuery, ignoreCase = true) || it.role.contains(searchQuery, ignoreCase = true)) &&
        (selectedDepartment == "All" || it.department == selectedDepartment)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Employees", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search employees...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            ScrollableTabRow(
                selectedTabIndex = departments.indexOf(selectedDepartment),
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                divider = {}
            ) {
                departments.forEach { dept ->
                    Tab(
                        selected = selectedDepartment == dept,
                        onClick = { selectedDepartment = dept },
                        text = { Text(dept) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (filteredEmployees.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No employees found.", color = TextSecondary)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filteredEmployees) { emp ->
                        Card(
                            modifier = Modifier.fillMaxWidth().clickable { navController.navigate(Screen.EmployeeDetail.createRoute(emp.id)) },
                            colors = CardDefaults.cardColors(containerColor = SurfaceColor),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(PrimaryColor.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                                    Text(emp.name.take(1).uppercase(), color = PrimaryColor, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(emp.name, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Text("${emp.role} • ${emp.department}", fontSize = 12.sp, color = TextSecondary)
                                }
                                IconButton(onClick = { employeeToEdit = emp }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = PrimaryColor)
                                }
                                IconButton(onClick = { viewModel.deleteEmployee(emp) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = AlertColor)
                                }
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            containerColor = PrimaryColor,
            contentColor = Color.White
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Employee")
        }

        if (showAddDialog || employeeToEdit != null) {
            EmployeeDialog(
                employee = employeeToEdit,
                onDismiss = { 
                    showAddDialog = false
                    employeeToEdit = null
                },
                onConfirm = { emp ->
                    if (employeeToEdit != null) viewModel.updateEmployee(emp)
                    else viewModel.addEmployee(emp)
                    showAddDialog = false
                    employeeToEdit = null
                }
            )
        }
    }
}

@Composable
fun EmployeeDialog(employee: Employee? = null, onDismiss: () -> Unit, onConfirm: (Employee) -> Unit) {
    var name by remember { mutableStateOf(employee?.name ?: "") }
    var role by remember { mutableStateOf(employee?.role ?: "") }
    var department by remember { mutableStateOf(employee?.department ?: "") }
    var email by remember { mutableStateOf(employee?.email ?: "") }
    var contact by remember { mutableStateOf(employee?.contact ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (employee == null) "Add Employee" else "Edit Employee") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = role, onValueChange = { role = it }, label = { Text("Role") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = department, onValueChange = { department = it }, label = { Text("Department") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = contact, onValueChange = { contact = it }, label = { Text("Contact") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm(Employee(
                    id = employee?.id ?: 0,
                    name = name, 
                    role = role, 
                    department = department, 
                    email = email, 
                    joiningDate = employee?.joiningDate ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()), 
                    contact = contact
                ))
            }) { Text(if (employee == null) "Add" else "Save") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeDetailScreen(employeeId: Int, viewModel: EptViewModel, navController: NavHostController) {
    val employees by viewModel.employees.collectAsState()
    val employee = employees.find { it.id == employeeId }
    val tasks by viewModel.tasks.collectAsState()
    val empTasks = tasks.filter { it.employeeId == employeeId }
    val performances by viewModel.performances.collectAsState()
    val empPerformances = performances.filter { it.employeeId == employeeId }
    
    var showAddTaskDialog by remember { mutableStateOf(false) }

    if (employee == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Employee not found")
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceColor)) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(80.dp).clip(CircleShape).background(PrimaryColor.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                        Text(employee.name.take(1).uppercase(), color = PrimaryColor, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(employee.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(employee.role, color = TextSecondary)
                    Text(employee.department, color = TextSecondary, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Joined: ${employee.joiningDate}", fontSize = 12.sp, color = TextSecondary)
                    Text(employee.email, fontSize = 12.sp, color = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Track Attendance", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { viewModel.recordAttendance(Attendance(employeeId = employeeId, date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()), status = "Present")) }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = AccentColor)) {
                    Text("Present")
                }
                OutlinedButton(onClick = { viewModel.recordAttendance(Attendance(employeeId = employeeId, date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()), status = "Absent")) }, modifier = Modifier.weight(1f)) {
                    Text("Absent")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Tasks & KPIs", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { showAddTaskDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task", tint = PrimaryColor)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            empTasks.forEach { task ->
                TaskItem(task, onStatusToggle = { 
                    val nextStatus = when(task.status) {
                        "Pending" -> "In Progress"
                        "In Progress" -> "Completed"
                        "Completed" -> "Reviewed"
                        else -> "Pending"
                    }
                    viewModel.updateTask(task.copy(status = nextStatus)) 
                })
            }
            if (empTasks.isEmpty()) Text("No tasks assigned", color = TextSecondary, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Performance History", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Button(onClick = { navController.navigate(Screen.PerformanceEvaluation.createRoute(employeeId)) }) {
                    Text("Evaluate")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            empPerformances.forEach { perf ->
                PerformanceCard(perf)
            }
            if (empPerformances.isEmpty()) Text("No reviews yet", color = TextSecondary, fontSize = 14.sp)
        }
        
        if (showAddTaskDialog) {
            AddTaskDialog(
                employees = listOf(employee),
                onDismiss = { showAddTaskDialog = false },
                onConfirm = { task ->
                    viewModel.addTask(task)
                    showAddTaskDialog = false
                }
            )
        }
    }
}

@Composable
fun TaskItem(task: Task, onStatusToggle: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = SurfaceColor)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val statusColor = when(task.status) {
                    "Completed" -> AccentColor
                    "Reviewed" -> PrimaryColor
                    "In Progress" -> Color(0xFFFF9800)
                    else -> TextSecondary
                }
                IconButton(onClick = onStatusToggle) {
                    Icon(
                        when(task.status) {
                            "Completed" -> Icons.Default.CheckCircle
                            "Reviewed" -> Icons.Default.ThumbUp
                            "In Progress" -> Icons.Default.PlayArrow
                            else -> Icons.Default.AddCircle
                        }, 
                        contentDescription = null, 
                        tint = statusColor
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(task.title, fontWeight = FontWeight.Medium)
                    Text("Status: ${task.status} • Due: ${task.deadline} • ${task.progress}%", fontSize = 12.sp, color = TextSecondary)
                }
            }
            LinearProgressIndicator(
                progress = { task.progress / 100f },
                modifier = Modifier.fillMaxWidth().height(4.dp).padding(top = 8.dp),
                color = if (task.status == "Completed") AccentColor else PrimaryColor,
                trackColor = Color.LightGray.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun PerformanceCard(perf: Performance) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = SurfaceColor)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(perf.period, fontWeight = FontWeight.Bold)
                Row {
                    Text(String.format("%.1f", perf.overallRating), fontWeight = FontWeight.Bold)
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFF9800), modifier = Modifier.size(16.dp))
                }
            }
            Text(perf.remarks, fontSize = 14.sp, color = TextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Q: ${perf.qualityScore}", fontSize = 10.sp, color = TextSecondary)
                Text("T: ${perf.timelinessScore}", fontSize = 10.sp, color = TextSecondary)
                Text("C: ${perf.communicationScore}", fontSize = 10.sp, color = TextSecondary)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerformanceEvaluationScreen(employeeId: Int, viewModel: EptViewModel, navController: NavHostController) {
    var period by remember { mutableStateOf("Q2 2025") }
    var quality by remember { mutableFloatStateOf(3f) }
    var timeliness by remember { mutableFloatStateOf(3f) }
    var attendance by remember { mutableFloatStateOf(3f) }
    var communication by remember { mutableFloatStateOf(3f) }
    var innovation by remember { mutableFloatStateOf(3f) }
    var remarks by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Evaluate Employee") }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp).verticalScroll(rememberScrollState())) {
            OutlinedTextField(value = period, onValueChange = { period = it }, label = { Text("Review Period") }, modifier = Modifier.fillMaxWidth())
            
            Spacer(modifier = Modifier.height(16.dp))
            MetricSlider("Quality of Work", quality) { quality = it }
            MetricSlider("Timeliness", timeliness) { timeliness = it }
            MetricSlider("Attendance", attendance) { attendance = it }
            MetricSlider("Communication", communication) { communication = it }
            MetricSlider("Innovation / Initiative", innovation) { innovation = it }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = remarks, onValueChange = { remarks = it }, label = { Text("Remarks") }, modifier = Modifier.fillMaxWidth().height(120.dp))
            
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                val overall = (quality + timeliness + attendance + communication + innovation) / 5f
                viewModel.addPerformance(Performance(
                    employeeId = employeeId,
                    period = period,
                    date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                    reviewerName = "Manager",
                    qualityScore = quality.toInt(),
                    timelinessScore = timeliness.toInt(),
                    attendanceScore = attendance.toInt(),
                    communicationScore = communication.toInt(),
                    innovationScore = innovation.toInt(),
                    overallRating = overall,
                    remarks = remarks
                ))
                navController.popBackStack()
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Submit Review")
            }
        }
    }
}

@Composable
fun MetricSlider(label: String, value: Float, onValueChange: (Float) -> Unit) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, fontSize = 14.sp)
            Text("${value.toInt()}/5", fontWeight = FontWeight.Bold, color = PrimaryColor)
        }
        Slider(value = value, onValueChange = onValueChange, valueRange = 1f..5f, steps = 3)
    }
}

@Composable
fun AnalyticsScreen(viewModel: EptViewModel) {
    val employees by viewModel.employees.collectAsState()
    val tasks by viewModel.tasks.collectAsState()
    val performances by viewModel.performances.collectAsState()

    Column(modifier = Modifier.padding(16.dp).fillMaxSize().verticalScroll(rememberScrollState())) {
        Text("Analytical Insights", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceColor)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Department Distribution", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                val deptCounts = employees.groupBy { it.department }.mapValues { it.value.size }
                deptCounts.forEach { (dept, count) ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(dept)
                        Text("$count", fontWeight = FontWeight.Bold)
                    }
                    LinearProgressIndicator(progress = { if (employees.isNotEmpty()) count.toFloat() / employees.size else 0f }, modifier = Modifier.fillMaxWidth().height(8.dp).padding(vertical = 4.dp), color = PrimaryColor, strokeCap = androidx.compose.ui.graphics.StrokeCap.Round)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceColor)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Productivity Trends", fontWeight = FontWeight.Bold)
                Text("Completed tasks ratio per employee", fontSize = 12.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(8.dp))
                employees.forEach { emp ->
                    val empTasks = tasks.filter { it.employeeId == emp.id }
                    val completed = empTasks.count { it.status == "Completed" || it.status == "Reviewed" }
                    val total = empTasks.size
                    val ratio = if (total > 0) completed.toFloat() / total else 0f
                    
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(emp.name, fontSize = 12.sp)
                            Text("${(ratio * 100).toInt()}%", fontSize = 10.sp, color = TextSecondary)
                        }
                        LinearProgressIndicator(progress = { ratio }, modifier = Modifier.fillMaxWidth().height(4.dp), color = AccentColor)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceColor)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Performance Leaderboard", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                val performers = employees.map { emp ->
                    val rating = performances.filter { it.employeeId == emp.id }.map { it.overallRating }.average().let { if (it.isNaN()) 0.0 else it }
                    emp to rating
                }.sortedByDescending { it.second }

                performers.take(5).forEachIndexed { index, (emp, rating) ->
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("${index + 1}.", fontWeight = FontWeight.Bold, modifier = Modifier.width(24.dp))
                        Text(emp.name, modifier = Modifier.weight(1f))
                        Text(String.format("%.1f", rating), fontWeight = FontWeight.Bold, color = PrimaryColor)
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFF9800), modifier = Modifier.size(14.dp))
                    }
                    if (index < performers.take(5).size - 1) HorizontalDivider(thickness = 0.5.dp, color = TextSecondary.copy(alpha = 0.2f))
                }
            }
        }
    }
}

@Composable
fun MetricRow(label: String, value: Double) {
    val displayValue = if (value.isNaN()) 0.0 else value
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 14.sp)
        Text(String.format("%.1f", displayValue), fontWeight = FontWeight.Bold, color = PrimaryColor)
    }
}

@Composable
fun SettingsScreen(viewModel: EptViewModel, navController: NavHostController) {
    val employees by viewModel.employees.collectAsState()
    val performances by viewModel.performances.collectAsState()
    
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text("Settings & Reports", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceColor)) {
            Column {
                ListItem(
                    headlineContent = { Text("Export Employee Report") },
                    supportingContent = { Text("CSV report of all employees and details") },
                    leadingContent = { Icon(Icons.Default.Share, contentDescription = null, tint = PrimaryColor) },
                    modifier = Modifier.clickable { println("Exporting Employee CSV...") }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("Export Performance Summary") },
                    supportingContent = { Text("Overall performance ratings for the current period") },
                    leadingContent = { Icon(Icons.Default.List, contentDescription = null, tint = AccentColor) },
                    modifier = Modifier.clickable { println("Exporting Performance CSV...") }
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text("App Theme") },
                    supportingContent = { Text("Switch between light and dark mode") },
                    leadingContent = { Icon(Icons.Default.Settings, contentDescription = null, tint = Color.Gray) },
                    modifier = Modifier.clickable { /* TODO */ }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = {
                viewModel.signOut()
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = AlertColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Logout")
        }
    }
}

@Composable
fun AddEmployeeDialog(onDismiss: () -> Unit, onConfirm: (Employee) -> Unit) {
    // Replaced by EmployeeDialog
}

@Composable
fun AddTaskDialog(employees: List<Employee>, onDismiss: () -> Unit, onConfirm: (Task) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var selectedEmployeeId by remember { mutableStateOf(if (employees.isNotEmpty()) employees[0].id else 0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Assign Task") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = deadline, onValueChange = { deadline = it }, label = { Text("Deadline (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
                
                if (employees.size > 1) {
                    Text("Assign to:", fontWeight = FontWeight.Medium)
                    employees.forEach { emp ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = selectedEmployeeId == emp.id, onClick = { selectedEmployeeId = emp.id })
                            Text(emp.name)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (selectedEmployeeId != 0) {
                    onConfirm(Task(employeeId = selectedEmployeeId, title = title, description = description, status = "Pending", priority = "Medium", deadline = deadline))
                }
            }, enabled = employees.isNotEmpty()) { Text("Assign") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun AddPerformanceDialog(employees: List<Employee>, onDismiss: () -> Unit, onConfirm: (Performance) -> Unit) {
    // Replaced by PerformanceEvaluationScreen
}
