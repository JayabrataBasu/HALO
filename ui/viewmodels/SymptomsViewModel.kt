package com.example.healthtracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.data.model.Symptom
import com.example.healthtracker.data.repository.SymptomRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SymptomsViewModel(
    private val repository: SymptomRepository
) : ViewModel() {
    val symptoms = repository.symptoms

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadSymptoms()
    }

    fun addSymptom(name: String, description: String, severity: Int) {
        viewModelScope.launch {
            try {
                val symptom = Symptom(
                    name = name,
                    description = description,
                    severity = severity
                )
                repository.addSymptom(symptom)
                
                if (symptom.isEmergency) {
                    _uiEvent.emit(UiEvent.EmergencyDetected(symptom))
                }
                
                _uiEvent.emit(UiEvent.SymptomAdded)
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }

    fun deleteSymptom(symptomId: String) {
        viewModelScope.launch {
            try {
                repository.removeSymptom(symptomId)
                _uiEvent.emit(UiEvent.SymptomDeleted)
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }

    private fun loadSymptoms() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                symptoms.collect { symptomList ->
                    _uiState.value = UiState.Success(symptomList)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val symptoms: List<Symptom>) : UiState()
        data class Error(val message: String) : UiState()
    }

    sealed class UiEvent {
        object SymptomAdded : UiEvent()
        object SymptomDeleted : UiEvent()
        data class EmergencyDetected(val symptom: Symptom) : UiEvent()
        data class Error(val message: String) : UiEvent()
    }
} 