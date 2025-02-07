package com.example.healthtracker.data.repository

import com.example.healthtracker.data.model.Symptom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

class SymptomRepository {
    private val _symptoms = MutableStateFlow<List<Symptom>>(emptyList())
    val symptoms = _symptoms.asStateFlow()

    fun addSymptom(symptom: Symptom) {
        _symptoms.update { currentList ->
            currentList + symptom
        }
    }

    fun removeSymptom(symptomId: String) {
        _symptoms.update { currentList ->
            currentList.filter { it.id != symptomId }
        }
    }

    fun getSymptomHistory(days: Int): List<Symptom> {
        val cutoffDate = LocalDateTime.now().minusDays(days.toLong())
        return _symptoms.value.filter { it.timestamp.isAfter(cutoffDate) }
    }

    fun getEmergencySymptoms(): List<Symptom> {
        return _symptoms.value.filter { it.isEmergency }
    }
} 