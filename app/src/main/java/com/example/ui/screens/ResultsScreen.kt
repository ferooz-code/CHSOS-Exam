package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.viewmodel.TestViewModel
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    viewModel: TestViewModel,
    onBackToHome: () -> Unit
) {
    val context = LocalContext.current
    val questions by viewModel.currentTestQuestions.collectAsState()
    val userAnswers by viewModel.userAnswers.collectAsState()
    val flagged by viewModel.flaggedQuestions.collectAsState()

    val correctAnswers = questions.filter { userAnswers[it.id] == it.correctAnswer }
    val score = correctAnswers.size
    val total = questions.size
    val percentage = if (total > 0) (score.toFloat() / total * 100).toInt() else 0

    val timeUsed = viewModel.testTimeUsedMillis
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeUsed)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeUsed) % 60

    fun exportResults() {
        val shareText = "My CHSOS Practice Test Results: $score / $total ($percentage%) in $minutes m $seconds s."
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(sendIntent, "Export Results"))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test Results") },
                navigationIcon = {
                    IconButton(onClick = onBackToHome) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Home")
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Your Score", style = MaterialTheme.typography.titleLarge)
            Text("$score / $total", style = MaterialTheme.typography.displayLarge, color = MaterialTheme.colorScheme.primary)
            Text("$percentage%", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Time used: ${minutes}m ${seconds}s", style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            val readinessMessage = if (percentage >= 80) "You are on track for the exam!" else "More study is recommended."
            Text(readinessMessage, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(24.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onBackToHome, modifier = Modifier.weight(1f)) {
                    Text("Return to Home")
                }
                OutlinedButton(onClick = { exportResults() }, modifier = Modifier.weight(1f)) {
                    Text("Export Results")
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("Review", style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))

            questions.forEach { q ->
                val userAnswer = userAnswers[q.id]
                val isCorrect = userAnswer == q.correctAnswer
                val isFlagged = flagged.contains(q.id)
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isCorrect) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.errorContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(if (isFlagged) "🚩 Flagged" else "", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error)
                            Text(if (isCorrect) "Correct" else "Incorrect", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(q.question, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Your answer: $userAnswer", style = MaterialTheme.typography.bodySmall)
                        Text("Correct answer: ${q.correctAnswer}", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(q.rationale, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
