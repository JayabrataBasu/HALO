import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SymptomsScreen(
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var symptoms by remember { mutableStateOf(listOf<Symptom>()) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Symptom")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Current Symptoms",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (symptoms.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Text(
                        text = "No symptoms recorded yet. Tap + to add symptoms.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(symptoms) { symptom ->
                        SymptomsCard(
                            symptom = symptom.name,
                            severity = symptom.severity,
                            description = symptom.description,
                            onDelete = {
                                symptoms = symptoms.filter { it != symptom }
                            }
                        )
                    }
                }
            }
        }

        if (showAddDialog) {
            AddSymptomDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, description, severity ->
                    symptoms = symptoms + Symptom(name, description, severity)
                    showAddDialog = false
                }
            )
        }
    }
}

data class Symptom(
    val name: String,
    val description: String,
    val severity: Int
) 