package com.example.employeeperformancetracker.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.employeeperformancetracker.viewmodel.EptViewModel
import com.example.employeeperformancetracker.viewmodel.UserRole

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Main : Screen("main")
    object EmployeeMain : Screen("employee_main")
    object EmployeeDetail : Screen("employee_detail/{employeeId}") {
        fun createRoute(employeeId: Int) = "employee_detail/$employeeId"
    }
    object PerformanceEvaluation : Screen("performance_evaluation/{employeeId}") {
        fun createRoute(employeeId: Int) = "performance_evaluation/$employeeId"
    }
}

@Composable
fun EptNavHost(navController: NavHostController, viewModel: EptViewModel) {
    val userRole by viewModel.userRole.collectAsState()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(onSplashFinished = {
                if (viewModel.isUserLoggedIn()) {
                    val startRoute = when (userRole) {
                        UserRole.ADMIN -> Screen.Main.route
                        UserRole.EMPLOYEE -> Screen.EmployeeMain.route
                        else -> Screen.Login.route
                    }
                    navController.navigate(startRoute) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                } else {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(viewModel = viewModel, onLoginSuccess = {
                val startRoute = when (userRole) {
                    UserRole.ADMIN -> Screen.Main.route
                    UserRole.EMPLOYEE -> Screen.EmployeeMain.route
                    else -> Screen.Main.route // Default to main if role not yet determined
                }
                navController.navigate(startRoute) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Main.route) {
            MainScreen(viewModel, navController)
        }
        composable(Screen.EmployeeMain.route) {
            EmployeeMainScreen(viewModel, navController)
        }
        composable(
            route = Screen.EmployeeDetail.route,
            arguments = listOf(navArgument("employeeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val employeeId = backStackEntry.arguments?.getInt("employeeId") ?: 0
            EmployeeDetailScreen(employeeId, viewModel, navController)
        }
        composable(
            route = Screen.PerformanceEvaluation.route,
            arguments = listOf(navArgument("employeeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val employeeId = backStackEntry.arguments?.getInt("employeeId") ?: 0
            PerformanceEvaluationScreen(employeeId, viewModel, navController)
        }
    }
}
