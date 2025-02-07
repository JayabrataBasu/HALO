package com.example.healthtracker.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(
    viewModel: SymptomsViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTimeRange by remember { mutableStateOf(TimeRange.Week) }
    val symptoms by viewModel.symptoms.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Time range selector
        TimeRangeSelector(
            selectedRange = selectedTimeRange,
            onRangeSelected = { selectedTimeRange = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Timeline
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(symptoms.groupBy { it.timestamp.toLocalDate() }) { (date, dailySymptoms) ->
                TimelineDay(
                    date = date,
                    symptoms = dailySymptoms
                )
            }
        }
    }
}

@Composable
private fun TimeRangeSelector(
    selectedRange: TimeRange,
    onRangeSelected: (TimeRange) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TimeRange.values().forEach { range ->
            FilterChip(
                selected = range == selectedRange,
                onClick = { onRangeSelected(range) },
                label = { Text(range.label) }
            )
        }
    }
}

@Composable
private fun TimelineDay(
    date: java.time.LocalDate,
    symptoms: List<Symptom>
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            symptoms.forEach { symptom ->
                TimelineItem(symptom = symptom)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun TimelineItem(
    symptom: Symptom
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = when {
                symptom.isEmergency -> Icons.Default.Warning
                symptom.severity >= 5 -> Icons.Default.Error
                else -> Icons.Default.Info
            },
            contentDescription = null,
            tint = when {
                symptom.isEmergency -> MaterialTheme.colorScheme.error
                symptom.severity >= 5 -> MaterialTheme.colorScheme.warning
                else -> MaterialTheme.colorScheme.primary
            }
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Column {
            Text(
                text = symptom.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = symptom.timestamp.format(DateTimeFormatter.ofPattern("HH:mm")),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Badge {
            Text("${symptom.severity}")
        }
    }
}

enum class TimeRange(val label: String, val days: Int) {
    Week("Week", 7),
    Month("Month", 30),
    ThreeMonths("3 Months", 90)
} 