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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyPlanScreen(
    viewModel: TestViewModel,
    onBack: () -> Unit
) {
    val tips by viewModel.studyTips.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Study Plan & Tips") },
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
            item {
                Text("Recommended Plan", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Based on typical preparation, here is a suggested timeline:")
                Spacer(modifier = Modifier.height(16.dp))
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("7-Day Plan", style = MaterialTheme.typography.titleMedium)
                        Text("Intensive review of weak domains, daily 50-question practice tests.")
                    }
                }
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("14-Day Plan", style = MaterialTheme.typography.titleMedium)
                        Text("Focus on one domain every 2 days. 20-question practice tests daily.")
                    }
                }
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("30-Day Plan", style = MaterialTheme.typography.titleMedium)
                        Text("Comprehensive review of blueprint, weekly 100-question practice tests.")
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("General Study Tips", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            items(tips) { tip ->
                ListItem(
                    headlineContent = { Text(tip.tip) },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Divider()
            }
        }
    }
}
