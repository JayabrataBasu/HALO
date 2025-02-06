package com.example.healthtracker.data.models

import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val badgeCount: Int = 0,
    val hasNews: Boolean = false
)
