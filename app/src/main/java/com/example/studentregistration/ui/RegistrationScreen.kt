package com.example.studentregistration.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.studentregistration.data.AppDatabase
import com.example.studentregistration.data.Students
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Form input states
    var name by rememberSaveable { mutableStateOf("") }
    var course by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableStateOf("") }

    // Error states
    var nameError by rememberSaveable { mutableStateOf<String?>(null) }
    var courseError by rememberSaveable { mutableStateOf<String?>(null) }
    var yearError by rememberSaveable { mutableStateOf<String?>(null) }

    // success confirmation state
    var successMessage by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student Registration") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // name field
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    if (nameError != null) nameError = null
                    if (successMessage.isNotBlank()) successMessage = ""
                },
                label = { Text("Name") },
                isError = nameError != null,
                supportingText = {
                    nameError?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Course field
            OutlinedTextField(
                value = course,
                onValueChange = {
                    course = it
                    if (courseError != null) courseError = null
                    if (successMessage.isNotBlank()) successMessage = ""
                },
                label = { Text("Course") },
                isError = courseError != null,
                supportingText = {
                    courseError?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // year field accepts numbers so invalid input like 5 can trigger button validation
            OutlinedTextField(
                value = year,
                onValueChange = { input ->
                    if (input.length <= 4 && input.all { it.isDigit() }) {
                        year = input
                        if (yearError != null) yearError = null
                        if (successMessage.isNotBlank()) successMessage = ""
                    }
                },
                label = { Text("Year (1-4)") },
                isError = yearError != null,
                supportingText = {
                    yearError?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // apply trim to avoid whitespace issues
                    val trimmedName = name.trim()
                    val trimmedCourse = course.trim()
                    val trimmedYear = year.trim()
                    val yearInt = trimmedYear.toIntOrNull()

                    // validate inputs
                    nameError = if (trimmedName.isBlank()) "Name cannot be empty" else null
                    courseError = if (trimmedCourse.isBlank()) "Course cannot be empty" else null
                    yearError = when {
                        trimmedYear.isBlank() -> "Year cannot be empty"
                        yearInt == null || yearInt !in 1..4 -> "Year must be a number between 1 and 4"
                        else -> null
                    }

                    // save if all validations pass
                    if (nameError == null && courseError == null && yearError == null && yearInt != null) {
                        coroutineScope.launch {
                            val db = AppDatabase.getDatabase(context)
                            val studentDao = db.studentDao()

                            val newStudent = Students(
                                name = trimmedName,
                                course = trimmedCourse,
                                year = yearInt
                            )

                            val generatedId = studentDao.insertStudent(newStudent)

                            successMessage = "Registered with ID $generatedId"

                            // clear text fields upon successful creation
                            name = ""
                            course = ""
                            year = ""
                        }
                    } else {
                        successMessage = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // confirmation display using theme-adaptive primary color
            if (successMessage.isNotBlank()) {
                Text(
                    text = successMessage,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}