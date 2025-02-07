import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SymptomsCard(
    symptom: String,
    severity: Int,
    description: String,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = symptom,
                    style = MaterialTheme.typography.titleLarge
                )
                
                Badge(
                    containerColor = when (severity) {
                        in 8..10 -> MaterialTheme.colorScheme.error
                        in 4..7 -> MaterialTheme.colorScheme.warning
                        else -> MaterialTheme.colorScheme.tertiary
                    }
                ) {
                    Text(
                        text = "$severity",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            }

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LinearProgressIndicator(
                progress = severity / 10f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = when (severity) {
                    in 8..10 -> MaterialTheme.colorScheme.error
                    in 4..7 -> MaterialTheme.colorScheme.warning
                    else -> MaterialTheme.colorScheme.tertiary
                }
            )

            TextButton(
                onClick = onDelete,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Remove")
            }
        }
    }
} 