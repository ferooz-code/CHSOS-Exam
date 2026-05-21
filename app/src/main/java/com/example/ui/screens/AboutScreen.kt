package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About the Exam") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "The CHSOS exam has 115 multiple-choice questions (100 scored and 15 unscored pilot questions). The time limit is 2 hours. Questions have 4 answer options.",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Domain Weights:", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text("1. Concepts in Healthcare: 10%")
            Text("2. Simulation Technology Operations: 35%")
            Text("3. Healthcare Simulation Practices: 25%")
            Text("4. Professional Role: 15%")
            Text("5. Concepts in Instructional Design: 15%")
            
            Spacer(modifier = Modifier.height(16.dp))
            Text("Testing Options:", style = MaterialTheme.typography.headlineSmall)
            Text("Can be taken at Prometric testing centers or through live remote proctoring.")
            
            Spacer(modifier = Modifier.height(16.dp))
            Text("Certification Period:", style = MaterialTheme.typography.headlineSmall)
            Text("Valid for 3 years.")
        }
    }
}
