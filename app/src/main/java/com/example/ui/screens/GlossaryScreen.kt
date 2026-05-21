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
fun GlossaryScreen(
    viewModel: TestViewModel,
    onBack: () -> Unit
) {
    val glossary by viewModel.glossary.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Glossary") },
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
            items(glossary) { term ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(term.term, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(term.definition, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
