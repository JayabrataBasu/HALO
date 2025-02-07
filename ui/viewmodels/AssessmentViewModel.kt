package com.example.healthtracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AssessmentViewModel : ViewModel() {
    private val _currentStep = MutableStateFlow(0)
    val currentStep = _currentStep.asStateFlow()

    private val _answers = MutableStateFlow<Map<Int, String>>(emptyMap())
    val answers = _answers.asStateFlow()

    private val _score = MutableStateFlow<Int?>(null)
    val score = _score.asStateFlow()

    fun answerQuestion(step: Int, answer: String) {
        _answers.update { current ->
            current + (step to answer)
        }
        
        if (step < 4) {
            _currentStep.value++
        } else {
            calculateFinalScore()
        }
    }

    fun goToPreviousStep() {
        if (_currentStep.value > 0) {
            _currentStep.value--
        }
    }

    private fun calculateFinalScore() {
        val finalScore = _answers.value.values.sumOf { answer ->
            when (answer) {
                "Severe", "Very Severe" -> 3
                "Moderate" -> 2
                else -> 1
            }
        }
        _score.value = finalScore
    }

    fun resetAssessment() {
        _currentStep.value = 0
        _answers.value = emptyMap()
        _score.value = null
    }
} 