package com.example.axiom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.axiom.databinding.FragmentSecondBinding
import androidx.appcompat.widget.SearchView
import androidx.core.view.children
import com.google.android.material.chip.Chip
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val selectedSymptoms = mutableSetOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSymptomsList()
        setupSearchView()


        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment)
        }
    }

    private fun setupSearchView() {
        (binding.searchView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true
            
            override fun onQueryTextChange(newText: String?): Boolean {
                filterSymptoms(newText)
                return true
            }
        })
    }

    private fun setupSymptomsList() {
        val symptoms = listOf(
            "Headache", "Fever", "Cough", "Fatigue",
            "Shortness of breath", "Muscle ache", "Loss of taste",
            "Sore throat", "Runny nose", "Nausea"
        )

        symptoms.forEach { symptom ->
            val chip = Chip(requireContext()).apply {
                text = symptom
                isCheckable = true
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) selectedSymptoms.add(symptom)
                    else selectedSymptoms.remove(symptom)
                    updateSelectedCount()
                }
            }
            binding.chipGroup.addView(chip)
        }
    }

    private fun filterSymptoms(query: String?) {
        binding.chipGroup.children.forEach { child ->
            if (child is Chip) {
                child.visibility = if (query.isNullOrEmpty() || 
                    child.text.contains(query, ignoreCase = true)
                ) View.VISIBLE else View.GONE
            }
        }
    }

    private fun updateSelectedCount() {
        binding.selectedCount.text = "Selected: ${selectedSymptoms.size}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


@Composable
fun SecondScreen() {
    var items by remember { mutableStateOf(listOf<String>()) }
    
    LaunchedEffect(Unit) {
        // Initialize your items
    }
    
    Column(modifier = Modifier.fillMaxSize()) {
        items.forEach { item ->
            // Your item composable
        }
    }
}