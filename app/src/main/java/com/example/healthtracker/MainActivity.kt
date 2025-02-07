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

/**
 * MainActivity is the main entry point of the HealthTracker application.
 * It sets up the content view using Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is starting.
     * It sets up the Compose content, applying the HealthTracker theme.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in onSaveInstanceState.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthTrackerTheme {
                MainScreen()
            }
        }
    }
}
/**
 * Represents a tab item in the bottom navigation bar.
 *
 * @property title The title of the tab.
 * @property icon The icon associated with the tab.
 */
data class TabItem(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
/**
 * Represents a symptom entry in the health tracker.
 *
 * @property name The name of the symptom.
 * @property severity The severity level of the symptom (e.g., on a scale of 1-10).
 * @property timestamp The date and time when the symptom was recorded. Defaults to the current time.
 */
data class Symptom(
    val name: String,
    val severity: Int,
    val timestamp: LocalDateTime = LocalDateTime.now()
)

/**
 * The main screen of the HealthTracker application.
 *
 * This composable function sets up the top app bar, bottom navigation bar,
 * and the main content area that changes based on the selected tab.
 */
@OptIn(ExperimentalMaterial3Api::class)
/**
 * The main screen of the HealthTracker application.
 *
 * This composable function sets up the top app bar, bottom navigation bar,
 * and the main content area that changes based on the selected tab.
 *
 */
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

/**
 * Composable function for displaying the symptoms screen.
 * This function includes error handling.
 */
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

/**
 * Composable function for displaying the triage screen.
 *
 * This screen provides quick access to emergency services and a quick assessment feature.
 * It includes:
 * - An emergency services card with a button to call emergency services.
 * - A quick assessment card with a button to start an assessment.
 */

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

/**
 * Composable function for displaying the history screen.
 * Currently, this screen only displays a placeholder message.
 */
@Composable
fun HistoryScreen() {
    // Implement history screen with a timeline of past symptoms and assessments
    Text("History Screen - Implementation pending")
}
/**
 * Composable function for displaying the profile screen.
 * This screen displays personal information in a card format.
 * It includes fields for Name, Age, Blood Type, and Emergency Contact.
 */
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

/**
 * Composable function for displaying a profile field.
 *
 * @param label The label text for the field.
 * @param value The value text for the field.
 * Displays the label and value in a column.
 */
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

/**
 * Preview function for the MainScreen.
 *
 * This allows developers to preview the UI design of the MainScreen composable.
 */
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    HealthTrackerTheme {
        MainScreen()
    }
}