package com.example.employeeperformancetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.employeeperformancetracker.ui.EptNavHost
import com.example.employeeperformancetracker.ui.theme.EmployeePerformanceTrackerTheme
import com.example.employeeperformancetracker.viewmodel.EptViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeePerformanceTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: EptViewModel = viewModel()
                    val navController = rememberNavController()
                    
                    // Seed data if the database is empty
                    LaunchedEffect(Unit) {
                        if (viewModel.employees.value.isEmpty()) {
                            viewModel.seedData()
                        }
                    }

                    EptNavHost(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}
