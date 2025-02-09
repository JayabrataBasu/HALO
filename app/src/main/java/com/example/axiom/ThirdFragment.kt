package com.example.axiom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.axiom.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMedicalHistory()

        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FourthFragment)
        }
    }

    private fun setupMedicalHistory() {
        // Example medical history items
        val historyItems = listOf(
            HistoryItem(
                "Annual Checkup",
                "Dr. Smith",
                "02/01/2024",
                "Regular checkup - all vitals normal",
                R.drawable.ic_check_circle
            ),
            HistoryItem(
                "Vaccination",
                "City Hospital",
                "01/15/2024",
                "COVID-19 Booster shot",
                R.drawable.ic_vaccine
            ),
            HistoryItem(
                "Blood Test",
                "Medical Lab",
                "12/20/2023",
                "Routine blood work - results pending",
                R.drawable.ic_bloodtype
            )
        )

        historyItems.forEach { item ->
            val historyItemView = layoutInflater.inflate(
                R.layout.item_medical_history,
                binding.timelineContainer,
                false
            )

            historyItemView.apply {
                findViewById<TextView>(R.id.titleText).text = item.title
                findViewById<TextView>(R.id.doctorText).text = item.doctor
                findViewById<TextView>(R.id.dateText).text = item.date
                findViewById<TextView>(R.id.descriptionText).text = item.description
                findViewById<ImageView>(R.id.iconImage).setImageResource(item.icon)

                setOnClickListener {
                    // Toggle expanded state
                    findViewById<TextView>(R.id.descriptionText).apply {
                       visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE

                    }
                }
            }

            binding.timelineContainer.addView(historyItemView)
        }
    }

    data class HistoryItem(
        val title: String,
        val doctor: String,
        val date: String,
        val description: String,
        val icon: Int
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 