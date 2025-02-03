import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding  // Changed this import
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Symptoms", "Triage", "History")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Health Tracker") }
            )
        },
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Filled.Favorite, contentDescription = title) },
                        label = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {  // Changed Padding to padding
            when (selectedTab) {
                0 -> SymptomsScreen()
                1 -> TriageScreen()
                2 -> HistoryScreen()
            }
        }
    }
}

@Composable
fun HistoryScreen() {
    TODO("Not yet implemented")
}

@Composable
fun TriageScreen() {
    TODO("Not yet implemented")
}

@Composable
fun SymptomsScreen() {
    TODO("Not yet implemented")
}
