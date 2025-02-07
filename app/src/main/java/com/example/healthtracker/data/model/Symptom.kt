package com.example.healthtracker.data.model

import java.time.LocalDateTime

data class Symptom(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val severity: Int,
    val bodyLocation: String? = null,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val duration: String? = null,
    val isEmergency: Boolean = severity >= 8
) 