package com.example.healthtracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthtracker.ui.theme.HealthTrackerTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthTrackerTheme {
                MainScreen()
            }
        }
    }
}

data class TabItem(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

data class Symptom(
    val name: String,
    val severity: Int,
    val timestamp: LocalDateTime = LocalDateTime.now()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    var showMenu by remember { mutableStateOf(false) }

    val tabs = listOf(
        TabItem("Symptoms", Icons.Default.Favorite),
        TabItem("Triage", Icons.Default.Home),
        TabItem("History", Icons.AutoMirrored.Filled.List),
        TabItem("Profile", Icons.Default.Person)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HALO: Health Assistant") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, "More options")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = { /* Handle settings */ },
                            leadingIcon = { Icon(Icons.Default.Settings, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Help") },
                            onClick = { /* Handle help */ },
                            leadingIcon = { Icon(Icons.Default.Warning, null) }
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = tab.icon, contentDescription = tab.title) },
                        label = { Text(tab.title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> SymptomsScreen()
                1 -> TriageScreen()
                2 -> HistoryScreen()
                3 -> ProfileScreen()
            }
        }
    }
}

@Composable
fun SymptomsScreen() {

    // Add error handling
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Show error message if present
    errorMessage?.let { message ->
        AlertDialog(
            onDismissRequest = { errorMessage = null },
            title = { Text("Error") },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = { errorMessage = null }) {
                    Text("OK")
                }
            }
        )
    }

    // Rest of the implementation
}


// Rest of your code remains the same, just remove the empty format function


private fun Any.format(dateTimeFormatter: DateTimeFormatter?) {

}

@Composable
fun TriageScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Emergency Services",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { /* Handle emergency call */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Call Emergency")
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Quick Assessment",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { /* Start assessment */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Assessment")
                }
            }
        }
    }
}

@Composable
fun HistoryScreen() {
    // Implement history screen with a timeline of past symptoms and assessments
    Text("History Screen - Implementation pending")
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Personal Information",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(16.dp))
                ProfileField("Name", "John Doe")
                ProfileField("Age", "30")
                ProfileField("Blood Type", "O+")
                ProfileField("Emergency Contact", "+1 234 567 8900")
            }
        }
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            label,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    HealthTrackerTheme {
        MainScreen()
    }
}