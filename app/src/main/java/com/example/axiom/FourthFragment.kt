package com.example.axiom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.axiom.databinding.FragmentFourthBinding
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.res.ResourcesCompat

class FourthFragment : Fragment() {

    private var _binding: FragmentFourthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupProfileFields()
        setupEditButton()
    }

    private fun setupProfileFields() {
        // Initially set fields to non-editable
        binding.apply {
            nameEdit.isEnabled = false
            dobEdit.isEnabled = false
            phoneEdit.isEnabled = false
            emailEdit.isEnabled = false
            insuranceEdit.isEnabled = false
            policyEdit.isEnabled = false
        }
    }

    private fun setupEditButton() {
        var isEditing = false

        binding.editButton.setOnClickListener {
            isEditing = !isEditing
            
            // Toggle edit state
            binding.apply {
                nameEdit.isEnabled = isEditing
                dobEdit.isEnabled = isEditing
                phoneEdit.isEnabled = isEditing
                emailEdit.isEnabled = isEditing
                insuranceEdit.isEnabled = isEditing
                policyEdit.isEnabled = isEditing

                // Change button appearance
                editButton.apply {
                    text = if (isEditing) "Save" else "Edit"
                    icon = ResourcesCompat.getDrawable(
                        resources,
                        if (isEditing) R.drawable.ic_save else R.drawable.ic_edit,
                        null
                    )
                }

                // Show success message when saving
                if (!isEditing) {
                    Snackbar.make(root, "Profile updated successfully", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 