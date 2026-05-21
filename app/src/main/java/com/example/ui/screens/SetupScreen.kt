package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.TestSettings
import com.example.viewmodel.TestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupScreen(
    viewModel: TestViewModel,
    onBack: () -> Unit,
    onStart: () -> Unit
) {
    val settings by viewModel.activeSettings.collectAsState()
    
    var questionCount by remember { mutableStateOf(settings.questionCount) }
    var studyMode by remember { mutableStateOf(settings.studyMode) }
    var randomize by remember { mutableStateOf(settings.randomize) }
    var domain by remember { mutableStateOf(settings.domain) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test Setup") },
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
            Text("Question Count", style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = questionCount == 10, onClick = { questionCount = 10 })
                Text("10")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(selected = questionCount == 20, onClick = { questionCount = 20 })
                Text("20")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(selected = questionCount == 50, onClick = { questionCount = 50 })
                Text("50")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(selected = questionCount == 100, onClick = { questionCount = 100 })
                Text("100")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Mode", style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = studyMode, onClick = { studyMode = true })
                Text("Study (Show answers)")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(selected = !studyMode, onClick = { studyMode = false })
                Text("Exam (Answers at end)")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = randomize, onCheckedChange = { randomize = it })
                Text("Randomize Questions")
            }

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    viewModel.updateSettings(TestSettings(
                        questionCount = questionCount,
                        studyMode = studyMode,
                        randomize = randomize,
                        domain = domain
                    ))
                    viewModel.startTest()
                    onStart()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Begin Practice Test")
            }
        }
    }
}
