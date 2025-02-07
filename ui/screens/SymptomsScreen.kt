import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.healthtracker.data.model.Symptom
import java.time.format.DateTimeFormatter

@Composable
fun SymptomsScreen(
    modifier: Modifier = Modifier,
    onEmergencyDetected: (Symptom) -> Unit = {}
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var symptoms by remember { mutableStateOf(listOf<Symptom>()) }
    var selectedSymptom by remember { mutableStateOf<Symptom?>(null) }

    LaunchedEffect(symptoms) {
        symptoms.find { it.isEmergency }?.let { emergencySymptom ->
            onEmergencyDetected(emergencySymptom)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Symptom")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Header with symptom count
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Current Symptoms",
                    style = MaterialTheme.typography.headlineMedium
                )
                Badge {
                    Text("${symptoms.size}")
                }
            }

            if (symptoms.isEmpty()) {
                EmptyStateMessage()
            } else {
                SymptomsList(
                    symptoms = symptoms,
                    onSymptomClick = { selectedSymptom = it },
                    onDeleteSymptom = { symptom ->
                        symptoms = symptoms.filter { it.id != symptom.id }
                    }
                )
            }
        }

        if (showAddDialog) {
            AddSymptomDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, description, severity ->
                    val newSymptom = Symptom(
                        name = name,
                        description = description,
                        severity = severity
                    )
                    symptoms = symptoms + newSymptom
                    showAddDialog = false
                }
            )
        }

        selectedSymptom?.let { symptom ->
            SymptomDetailsDialog(
                symptom = symptom,
                onDismiss = { selectedSymptom = null }
            )
        }
    }
}

@Composable
private fun EmptyStateMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.AddCircle,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "No symptoms recorded yet",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Tap + to add symptoms",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SymptomDetailsDialog(
    symptom: Symptom,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = symptom.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Recorded on: ${symptom.timestamp.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"))}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Severity: ${symptom.severity}/10",
                    style = MaterialTheme.typography.titleMedium
                )
                LinearProgressIndicator(
                    progress = symptom.severity / 10f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = symptom.description,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(16.dp))
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Close")
                }
            }
        }
    }
} 