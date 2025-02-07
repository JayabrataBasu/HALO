import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TriageScreen(
    modifier: Modifier = Modifier
) {
    var currentStep by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Symptom Assessment",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LinearProgressIndicator(
            progress = (currentStep + 1) / 5f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Question ${currentStep + 1} of 5",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = getQuestionForStep(currentStep),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    getOptionsForStep(currentStep).forEach { option ->
                        OutlinedButton(
                            onClick = {
                                if (currentStep < 4) currentStep++
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(option)
                        }
                    }
                }
            }
        }

        if (currentStep > 0) {
            TextButton(
                onClick = { currentStep-- },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Previous Question")
            }
        }

        if (currentStep == 4) {
            Button(
                onClick = { /* Handle completion */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Complete Assessment")
            }
        }
    }
}

private fun getQuestionForStep(step: Int): String {
    return when (step) {
        0 -> "How long have you been experiencing these symptoms?"
        1 -> "What is the intensity of your main symptom?"
        2 -> "Are you experiencing any of these emergency symptoms?"
        3 -> "Have you taken any medication for these symptoms?"
        4 -> "Do you have any chronic medical conditions?"
        else -> ""
    }
}

private fun getOptionsForStep(step: Int): List<String> {
    return when (step) {
        0 -> listOf("Less than 24 hours", "1-3 days", "4-7 days", "More than a week")
        1 -> listOf("Mild", "Moderate", "Severe", "Very Severe")
        2 -> listOf("Difficulty breathing", "Chest pain", "Severe headache", "None of the above")
        3 -> listOf("Yes, it helped", "Yes, no effect", "No, haven't tried any", "Not sure")
        4 -> listOf("Diabetes", "Heart condition", "Asthma", "None")
        else -> emptyList()
    }
} 