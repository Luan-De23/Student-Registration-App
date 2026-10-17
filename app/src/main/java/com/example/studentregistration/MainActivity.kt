package com.example.studentregistration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.studentregistration.ui.theme.StudentRegistrationTheme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studentregistration.ui.HomeScreen
import com.example.studentregistration.ui.RegistrationScreen
import com.example.studentregistration.ui.SearchScreen
import com.example.studentregistration.ui.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // nav controller
            val navController = rememberNavController()

            // NavHost
            StudentRegistrationTheme {
                NavHost(
                    navController = navController,
                    startDestination = Routes.HOME
                ) {
                    composable(Routes.HOME) {
                        HomeScreen(
                            onNavigateToRegistration = { navController.navigate(Routes.REGISTRATION) },
                            onNavigateToSearch = { navController.navigate(Routes.SEARCH) }
                        )
                    }
                    composable(Routes.REGISTRATION) {
                        RegistrationScreen(
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                    composable(Routes.SEARCH) {
                        SearchScreen(
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}


