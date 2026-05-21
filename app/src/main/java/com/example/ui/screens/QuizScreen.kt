package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.viewmodel.TestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: TestViewModel,
    onSubmit: () -> Unit
) {
    val questions by viewModel.currentTestQuestions.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val userAnswers by viewModel.userAnswers.collectAsState()
    val flagged by viewModel.flaggedQuestions.collectAsState()
    val settings by viewModel.activeSettings.collectAsState()
    
    if (questions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No questions available for this selection.")
        }
        return
    }

    val currentQuestion = questions[currentIndex]
    val currentAnswer = userAnswers[currentQuestion.id]
    val isFlagged = flagged.contains(currentQuestion.id)
    val isAnswered = currentAnswer != null

    var showExplanation by remember(currentQuestion.id) { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Question ${currentIndex + 1} of ${questions.size}") },
                actions = {
                    IconButton(onClick = { viewModel.toggleFlag(currentQuestion.id) }) {
                        Icon(
                            Icons.Filled.Warning,
                            contentDescription = "Flag Question",
                            tint = if (isFlagged) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { viewModel.previousQuestion() }, enabled = currentIndex > 0) {
                        Text("Previous")
                    }
                    if (currentIndex == questions.size - 1) {
                        Button(onClick = { 
                            viewModel.submitTest()
                            onSubmit() 
                        }) {
                            Text("Submit Test")
                        }
                    } else {
                        Button(onClick = { viewModel.nextQuestion() }) {
                            Text("Next")
                        }
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / questions.size.toFloat() },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Badge { Text("Domain ${currentQuestion.domainNumber}") }
                Badge(containerColor = MaterialTheme.colorScheme.secondaryContainer) { Text(currentQuestion.difficulty) }
            }
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(currentQuestion.question, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(24.dp))
            
            // Options
            currentQuestion.options.forEach { (key, value) ->
                val isSelected = currentAnswer == key
                val cardColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    onClick = {
                        if (!settings.studyMode || !showExplanation) {
                            viewModel.answerQuestion(currentQuestion.id, key)
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("$key.", fontWeight = FontWeight.Bold, modifier = Modifier.width(30.dp))
                        Text(value)
                    }
                }
            }

            if (settings.studyMode && isAnswered) {
                Spacer(modifier = Modifier.height(16.dp))
                if (!showExplanation) {
                    Button(onClick = { showExplanation = true }, modifier = Modifier.fillMaxWidth()) {
                        Text("Check Answer")
                    }
                } else {
                    val correct = currentAnswer == currentQuestion.correctAnswer
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = if (correct) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(if (correct) "Correct!" else "Incorrect. The correct answer is ${currentQuestion.correctAnswer}.", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Rationale:", fontWeight = FontWeight.Bold)
                            Text(currentQuestion.rationale)
                            
                            val incMap = currentQuestion.whyIncorrect
                            if (incMap != null && incMap.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Why others are incorrect:", fontWeight = FontWeight.Bold)
                                incMap.forEach { (k, v) ->
                                    Text("$k: $v")
                                }
                            }
                            
                            if (currentQuestion.whyThisMatters.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Why this matters in operations:", fontWeight = FontWeight.Bold)
                                Text(currentQuestion.whyThisMatters)
                            }
                        }
                    }
                }
            }
        }
    }
}
