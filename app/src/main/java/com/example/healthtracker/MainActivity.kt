package com.example.healthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.healthtracker.ui.theme.HealthTrackerTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector

private val Icons.Filled.Help: ImageVector
    get() = Icons.Default.Help

private val Emergency: ImageVector
    get() = Icons.Default.Warning // Using Warning icon as emergency indicator

private val Icons.Outlined.History: ImageVector
    get() = Icons.Outlined.History

private val Icons.Filled.History: ImageVector 
    get() = Icons.Default.History

private val Icons.Outlined.LocalHospital: ImageVector
    get() = Icons.Outlined.LocalHospital

/**
 * MainActivity serves as the entry point for the HALO Health Assistant application.
 * It implements a responsive Material Design 3 interface with adaptive navigation patterns
 * based on screen size and orientation.
 */
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

/**
 * Represents a navigation destination in the app
 * @property title The display title for the destination
 * @property selectedIcon Icon shown when the destination is selected
 * @property unselectedIcon Icon shown when the destination is not selected
 * @property route The navigation route identifier
 */
data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

/**
 * Main composable that sets up the app's navigation structure and UI scaffolding
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    var showMenu by remember { mutableStateOf(false) }

    // Define navigation items with both selected and unselected icons
    val navigationItems = listOf(
        NavigationItem(
            "Symptoms",
            Icons.Filled.Favorite,
            Icons.Outlined.FavoriteBorder,
            "symptoms"
        ),
        NavigationItem(
            "Triage",
            Icons.Filled.Info,
            Icons.Outlined.LocalHospital,
            "triage"
        ),
        NavigationItem(
            "History",
            Icons.Filled.History,
            Icons.Outlined.History,
            "history"
        ),
        NavigationItem(
            "Profile",
            Icons.Filled.Person,
            Icons.Outlined.Person,
            "profile"
        )
    )

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "HALO: Health Assistant",
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    // Emergency button
                    Button(
                        onClick = { /* Handle emergency */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    ) {
                        Icon(Emergency, "Emergency")
                        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                        Text("Emergency")
                    }
                    
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
                            leadingIcon = { Icon(Icons.Default.Help, null.toString()) }
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = { Text("About") },
                            onClick = { /* Handle about */ },
                            leadingIcon = { Icon(Icons.Default.Info, null) }
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == index) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                },
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        // Main content area with animations between screens
        AnimatedContent(
            targetState = selectedTab,
            transitionSpec = {
                (fadeIn() + slideInHorizontally(
                    initialOffsetX = { if (targetState > initialState) it else -it }
                )).togetherWith(fadeOut() + slideOutHorizontally(
                    targetOffsetX = { if (targetState > initialState) -it else it }
                ))
            },
            modifier = Modifier.padding(innerPadding)
        ) { targetTab ->
            when (targetTab) {
                0 -> SymptomsScreen()
                1 -> TriageScreen()
                2 -> HistoryScreen()
                3 -> ProfileScreen()
            }
        }
    }
}

fun Icon(help: Any, nothing: String) {

}

/**
 * Profile screen implementation showing user information
 */
@Composable
fun ProfileScreen() {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var bloodType by remember { mutableStateOf("") }
    var emergencyContact by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Personal Profile",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                ProfileField(
                    label = "Full Name",
                    value = name,
                    onValueChange = { name = it }
                )
                ProfileField(
                    label = "Age",
                    value = age,
                    onValueChange = { age = it }
                )
                ProfileField(
                    label = "Blood Type",
                    value = bloodType,
                    onValueChange = { bloodType = it }
                )
                ProfileField(
                    label = "Emergency Contact",
                    value = emergencyContact,
                    onValueChange = { emergencyContact = it }
                )
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

fun Column(modifier: Modifier, horizontalAlignment: Unit, content: @Composable() (ColumnScope.() -> Unit)) {
    TODO("Not yet implemented")

}

class Alignment {
    companion object {
            val CenterHorizontally: Unit = Unit

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
 * Composable function for displaying a profile field.
 *
 * @param label The label text for the field.
 * @param value The value text for the field.
 * @param onValueChange Callback function to update the value.
 * Displays the label and value in a column.
 */
@Composable
fun ProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            label,
            style = MaterialTheme.typography.bodySmall
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
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

annotation class Preview(val showBackground: Boolean)
