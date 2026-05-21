package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.TestSettings
import com.example.viewmodel.TestViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel: TestViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") {
            HomeScreen(
                onNavigateToSetup = { navController.navigate("setup") },
                onNavigateToDomains = { navController.navigate("dashboard") },
                onNavigateToStudyPlan = { navController.navigate("studyPlan") },
                onNavigateToAbout = { navController.navigate("about") },
                onNavigateToGlossary = { navController.navigate("glossary") }
            )
        }
        composable("about") {
            AboutScreen(onBack = { navController.popBackStack() })
        }
        composable("dashboard") {
            DashboardScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onPracticeDomain = { domainId ->
                    viewModel.updateSettings(TestSettings(domain = domainId))
                    navController.navigate("setup")
                }
            )
        }
        composable("setup") {
            SetupScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onStart = { navController.navigate("quiz") }
            )
        }
        composable("quiz") {
            QuizScreen(
                viewModel = viewModel,
                onSubmit = {
                    navController.navigate("results") {
                        popUpTo("home")
                    }
                }
            )
        }
        composable("results") {
            ResultsScreen(
                viewModel = viewModel,
                onBackToHome = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
        composable("studyPlan") {
            StudyPlanScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable("glossary") {
            GlossaryScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
    }
}
