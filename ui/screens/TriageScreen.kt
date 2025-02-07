import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.ui.graphics.Color

@Composable
fun TriageScreen(
    modifier: Modifier = Modifier,
    onEmergencyCall: () -> Unit = {},
    onStartAssessment: () -> Unit = {}
) {
    var showEmergencyDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Emergency Card
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        Icons.Filled.Emergency,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Emergency Services",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                
                Button(
                    onClick = { showEmergencyDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Call, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Call Emergency Services")
                }
            }
        }

        // Quick Assessment Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Symptom Assessment",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Take a quick assessment to evaluate your symptoms",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onStartAssessment,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Assessment")
                }
            }
        }
    }

    if (showEmergencyDialog) {
        AlertDialog(
            onDismissRequest = { showEmergencyDialog = false },
            title = {
                Text("Emergency Call")
            },
            text = {
                Text("Are you sure you want to call emergency services?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        onEmergencyCall()
                        showEmergencyDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Call Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEmergencyDialog = false }) {
                    Text("Cancel")
                }
            }
        )
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