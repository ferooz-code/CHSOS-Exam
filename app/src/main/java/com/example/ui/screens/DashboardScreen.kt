package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.TestViewModel
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: TestViewModel,
    onBack: () -> Unit,
    onPracticeDomain: (String) -> Unit
) {
    val domains by viewModel.domains.collectAsState()
    val attempts by viewModel.testAttempts.collectAsState(initial = emptyList())

    // Calculate performance per domain based on past attempts
    // This is a simplified calculation: sum of correct per domain across all attempts / total possible? 
    // Actually, we don't have total possible per domain stored in the attempt easily, but we can just show total correct per domain across attempts.
    val correctCounts = mutableMapOf<Int, Int>()
    attempts.forEach { attempt ->
        val json = JSONObject(attempt.domainScoresJson)
        json.keys().forEach { key ->
            val kInt = key.toInt()
            correctCounts[kInt] = (correctCounts[kInt] ?: 0) + json.getInt(key)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Domain Dashboard") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(domains) { domain ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(domain.name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Weight: ${domain.weight}%", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(domain.description, style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Key KSA: ${domain.keyKsa}", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Performance indicator
                        val correct = correctCounts[domain.id.toInt()] ?: 0
                        Text("Total Correct in Practice: $correct", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { onPracticeDomain(domain.id) }) {
                            Text("Practice this Domain")
                        }
                    }
                }
            }
        }
    }
}
