import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "High Priority", "Medium", "Low")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Medical History",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Filter chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            items(filters) { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) }
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Example history items
            items(10) { index ->
                HistoryItem(
                    date = "2024-03-${20 - index}",
                    description = "Symptom check #${index + 1}",
                    priority = when (index % 3) {
                        0 -> "High"
                        1 -> "Medium"
                        else -> "Low"
                    }
                )
            }
        }
    }
}

@Composable
fun HistoryItem(
    date: String,
    description: String,
    priority: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            FilterChip(
                selected = false, // Since it's a visual indicator, not a filter
                onClick = { /* Handle click if needed */ },
                label = { Text(priority) },
                colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                    containerColor = when (priority) {
                        "High" -> MaterialTheme.colorScheme.errorContainer
                        "Medium" -> MaterialTheme.colorScheme.errorContainer
                        else -> MaterialTheme.colorScheme.tertiaryContainer
                    },
                  labelColor = when (priority) {
                        "High" -> MaterialTheme.colorScheme.onErrorContainer
                        "Medium" -> MaterialTheme.colorScheme.onErrorContainer
                        else -> MaterialTheme.colorScheme.onTertiaryContainer
                    },

                )
            )
            }
        }
    }


fun Chip(
    onClick: () -> Unit,
    colors: ChipColors,
    modifier: Nothing,
    enabled: Nothing,
    label: Nothing,
    labelTextStyle: Nothing,
    labelColor: Nothing,
    leadingIcon: Nothing,
    trailingIcon: Nothing,
    shape: Nothing,
    elevation: Nothing,
    border: Nothing,
    minHeight: Nothing,
    paddingValues: Nothing,
    interactionSource: @Composable () -> Unit
) {

}

