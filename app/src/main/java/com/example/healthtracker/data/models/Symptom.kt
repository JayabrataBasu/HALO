package com.example.healthtracker.data.models



import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID
import java.time.DayOfWeek

data class Symptom(
    /**
     * The unique identifier for the symptom.
     * This is generated using UUID to ensure uniqueness.
     */

    /**
     * The severity of the symptom, typically on a scale of 1-10.
     */

    /**
     * The time at which the symptom was recorded.
     * Defaults to the current time if not specified.
     */
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val severity: Int,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val bodyLocation: String? = null,
    val description: String? = null,
    val duration: Int? = null, // in minutes
    val associatedSymptoms: List<String> = emptyList(),
    val isRecurring: Boolean = false
)

/**
 * Checks if the symptom is considered urgent based on severity and associated symptoms.
 * @return True if the symptom is considered urgent, false otherwise.
 */
fun Symptom.isUrgent(): Boolean =
    severity > 7 || associatedSymptoms.size > 3

/**
 * Calculates the duration of the symptom in hours.
 * @return The duration in hours, or 0 if the duration is not specified.
 */
fun Symptom.getDurationInHours(): Float =
    duration?.div(60f) ?: 0f


/**
 * Enum representing different categories of symptoms.
 */
enum class SymptomCategory {
    PAIN,
    /**
     * Respiratory symptoms, such as difficulty breathing.
     */
    /**
     * Digestive symptoms, such as nausea or abdominal pain.
     */
    /**
     * Neurological symptoms, such as headaches or dizziness.
     */
    RESPIRATORY,
    DIGESTIVE,
    NEUROLOGICAL,
    SKIN,
    OTHER
}
data class SymptomHistory(
    /**
     * The specific symptom entry.
     */
    /**
     * Notes about the progression of the symptom over time.
     */

    val symptom: Symptom,
    val progressNotes: List<String>,
    val medications: List<String>,
    val triggers: List<String>
)


/**
 * Class responsible for analyzing trends in a list of symptoms.
 * It provides insights into average severity, most frequent symptoms, peak times, and weekly patterns.
 */

class SymptomAnalytics {
    fun analyzeSymptomTrends(symptoms: List<Symptom>): SymptomTrends {
        return SymptomTrends(
            averageSeverity = calculateAverageSeverity(symptoms),
            mostFrequentSymptom = findMostFrequentSymptom(symptoms),
            peakTimes = findPeakTimes(symptoms),
            weeklyPattern = analyzeWeeklyPattern(symptoms)
        )
    }

    private fun analyzeWeeklyPattern(symptoms: List<Symptom>): Map<String, Int> {
        return symptoms
            .groupBy {
                (it.timestamp as LocalDateTime).dayOfWeek.toString()
            }
            .mapValues {
                it.value.size
            }
            .withDefault { 0 } // Provides default value for days with no symptoms
    }

    private fun calculateAverageSeverity(symptoms: List<Symptom>): Double {
        return symptoms.map { it.severity }.average()
    }

    private fun findMostFrequentSymptom(symptoms: List<Symptom>): String {
        return symptoms.groupBy { it.name }
            .maxByOrNull { it.value.size }
            ?.key ?: ""
    }

    private fun findPeakTimes(symptoms: List<Symptom>): List<Int> {
        return symptoms.groupBy { it.timestamp.hour }
            .map { it.key }
            .sortedBy { it }
    }

    data class SymptomTrends(
        val averageSeverity: Double,
        val mostFrequentSymptom: String,
        val peakTimes: List<Int>,
        val weeklyPattern: Map<String, Int>
    )
}

data class EnhancedSymptom(
    /**
     * Represents a symptom with enhanced details for comprehensive tracking.
     * This class includes detailed information such as specific body locations, characteristics, triggers,
     * relief factors, and the duration of the symptom.
     *
     * @property id Unique identifier for the enhanced symptom.
     * @property name Name of the symptom.
     * @property severity Severity level of the symptom.
     * @property timestamp The exact time when the symptom occurred.
     */
    val id: String,
    val name: String,
    val severity: Int,
    val timestamp: LocalDateTime,
    val bodyLocation: BodyLocation,
    val characteristics: List<SymptomCharacteristic>,
    val triggers: List<String>,
    val reliefFactors: List<String>,
    val duration: Duration
)

/**
 * Enum class representing possible body locations where a symptom can occur.
 */
enum class BodyLocation {
    HEAD, CHEST, ABDOMEN, BACK_UPPER, BACK_LOWER,
    ARM_LEFT, ARM_RIGHT, LEG_LEFT, LEG_RIGHT
}

/**
 * Enum class representing various characteristics that a symptom might have.
 */
enum class SymptomCharacteristic {
    SHARP, DULL, THROBBING, BURNING, STABBING,
    CRAMPING, ACHING, TINGLING
}

/**
 * Class responsible for tracking and analyzing symptoms over time.
 * It allows adding new symptom entries, retrieving symptom history based on different time frames,
 * and analyzing symptom patterns, such as frequency, average duration, and common triggers.
 *
 * This class acts as a central hub for managing symptom data and extracting meaningful information from it.
 */
class SymptomTracker {
    private val symptoms = mutableListOf<EnhancedSymptom>()

    fun addSymptomEntry(symptom: EnhancedSymptom) {
        symptoms.add(symptom)
        analyzeSymptomPattern(symptom)
    }

    fun getSymptomHistory(timeFrame: TimeFrame): List<EnhancedSymptom> {
        val cutoffTime = when(timeFrame) {
            TimeFrame.DAY -> LocalDateTime.now().minusDays(1)
            TimeFrame.WEEK -> LocalDateTime.now().minusWeeks(1)
            TimeFrame.MONTH -> LocalDateTime.now().minusMonths(1)
        }
        return symptoms.filter { it.timestamp.isAfter(cutoffTime) }
    }

    private fun analyzeSymptomPattern(symptom: EnhancedSymptom): SymptomPattern {
        val similarSymptoms = symptoms.filter { it.name == symptom.name }
        return SymptomPattern(
            frequency = calculateFrequency(similarSymptoms),
            averageDuration = calculateAverageDuration(similarSymptoms),
            commonTriggers = findCommonTriggers(similarSymptoms)
        )
    }
    private fun calculateFrequency(symptoms: List<EnhancedSymptom>): Double {
        val timeSpan = Duration.between(
            symptoms.minOfOrNull { it.timestamp } ?: LocalDateTime.now(),
            symptoms.maxOfOrNull { it.timestamp } ?: LocalDateTime.now()
        )
        val weeksPassed = timeSpan.toDays() / 7.0
        return if (weeksPassed > 0) symptoms.size / weeksPassed else 0.0
    }

    private fun calculateAverageDuration(symptoms: List<EnhancedSymptom>): Duration {
        return if (symptoms.isEmpty()) Duration.ZERO else
            symptoms.map { it.duration }
                .reduce { acc, duration -> acc.plus(duration) }
                .dividedBy(symptoms.size.toLong())
    }

    private fun findCommonTriggers(symptoms: List<EnhancedSymptom>): List<String> {
        return symptoms.flatMap { it.triggers }
            .groupBy { it }
            .mapValues { it.value.size }
            .entries
            .sortedByDescending { it.value }
            .take(3)  // Top 3 most common triggers
            .map { it.key }
    }

    private fun analyzeWeeklyPattern(symptoms: List<EnhancedSymptom>): Map<String, Int> {
        return symptoms.groupBy {
            it.timestamp.dayOfWeek.toString()
        }.mapValues {
            it.value.size
        }
    }
}

/**
 * Enum class representing different time frames for which symptom history can be retrieved.
 */
enum class TimeFrame {
    DAY, WEEK, MONTH
}

data class SymptomPattern(
    val frequency: Double, // occurrences per week
    val averageDuration: Duration,
    val commonTriggers: List<String>
)