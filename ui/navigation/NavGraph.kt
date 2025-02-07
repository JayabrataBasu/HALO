package com.example.healthtracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.healthtracker.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Symptoms.route
    ) {
        composable(Screen.Symptoms.route) {
            SymptomsScreen(
                onEmergencyDetected = { symptom ->
                    navController.navigate(Screen.Triage.route)
                }
            )
        }
        
        composable(Screen.Triage.route) {
            TriageScreen(
                onEmergencyCall = { /* Handle emergency call */ },
                onStartAssessment = {
                    navController.navigate(Screen.Assessment.route)
                }
            )
        }
        
        composable(Screen.History.route) {
            HistoryScreen()
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        
        composable(Screen.Assessment.route) {
            AssessmentScreen(
                onComplete = { result ->
                    navController.navigate(Screen.Results.createRoute(result))
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Symptoms : Screen("symptoms")
    object Triage : Screen("triage")
    object History : Screen("history")
    object Profile : Screen("profile")
    object Assessment : Screen("assessment")
    object Results : Screen("results/{score}") {
        fun createRoute(score: Int) = "results/$score"
    }
} 