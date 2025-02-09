package com.example.axiom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.axiom.databinding.FragmentFirstBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Show welcome dialog only first time
        if (savedInstanceState == null) {
            showWelcomeDialog()
        }

        setupHealthMetricsCards()

        binding.buttonAppointments.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun setupHealthMetricsCards() {
        // Example of dynamically updating health metrics
        binding.cardSteps.apply {
            metricsTitle.text = "Daily Steps"
            metricsValue.text = "8,439"
            metricsIcon.setImageResource(R.drawable.ic_directions_walk)
        }

        binding.cardHeartRate.apply {
            metricsTitle.text = "Heart Rate"
            metricsValue.text = "72 BPM"
            metricsIcon.setImageResource(R.drawable.ic_favorite)
        }

        binding.cardCalories.apply {
            metricsTitle.text = "Calories"
            metricsValue.text = "1,867 kcal"
            metricsIcon.setImageResource(R.drawable.ic_local_fire)
        }
    }

    private fun showWelcomeDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Welcome to HealthCare")
            .setMessage("Your personal health assistant")
            .setPositiveButton("Get Started") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}