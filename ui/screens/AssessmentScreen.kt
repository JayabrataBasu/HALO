package com.example.healthtracker.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AssessmentScreen(
    onComplete: (Int) -> Unit = {}
) {
    var currentStep by remember { mutableStateOf(0) }
    var answers by remember { mutableStateOf(mutableMapOf<Int, String>()) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = (currentStep + 1) / 5f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        
        AnimatedContent(
            targetState = currentStep,
            transitionSpec = {
                fadeIn() + slideInHorizontally() with 
                fadeOut() + slideOutHorizontally()
            }
        ) { step ->
            QuestionCard(
                question = getQuestionForStep(step),
                options = getOptionsForStep(step),
                selectedAnswer = answers[step],
                onAnswerSelected = { answer ->
                    answers[step] = answer
                    if (step < 4) {
                        currentStep++
                    } else {
                        val score = calculateScore(answers)
                        onComplete(score)
                    }
                }
            )
        }
        
        if (currentStep > 0) {
            TextButton(
                onClick = { currentStep-- },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Previous Question")
            }
        }
    }
}

@Composable
private fun QuestionCard(
    question: String,
    options: List<String>,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            options.forEach { option ->
                OutlinedButton(
                    onClick = { onAnswerSelected(option) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (option == selectedAnswer) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                ) {
                    Text(option)
                }
            }
        }
    }
}

private fun calculateScore(answers: Map<Int, String>): Int {
    // Implement scoring logic based on answers
    return answers.values.sumOf { answer ->
        when (answer) {
            "Severe", "Very Severe" -> 3
            "Moderate" -> 2
            else -> 1
        }
    }
} 