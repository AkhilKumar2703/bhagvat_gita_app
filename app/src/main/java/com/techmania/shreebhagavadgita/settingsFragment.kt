package com.techmania.shreebhagavadgita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.techmania.shreebhagavadgita.databinding.FragmentSettingsBinding


class settingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)

        binding.llsavedChapters.setOnClickListener { findNavController().navigate(R.id.action_settingsFragment_to_savedChaptersFragment) }
        binding.llsavedVerses.setOnClickListener { findNavController().navigate(R.id.action_settingsFragment_to_savedVersesFragment) }
        return binding.root

    }


}