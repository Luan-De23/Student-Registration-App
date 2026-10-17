package com.example.studentregistration.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToRegistration: () -> Unit,
    onNavigateToSearch: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Home",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNavigateToRegistration,
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Registration")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToSearch,
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Search")
            }
        }
    }
}