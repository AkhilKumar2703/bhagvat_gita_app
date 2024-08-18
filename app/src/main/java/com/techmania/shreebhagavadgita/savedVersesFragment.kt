package com.techmania.shreebhagavadgita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.techmania.shreebhagavadgita.databinding.FragmentSavedVersesBinding
import com.techmania.shreebhagavadgita.datasource.api.room.SavedVerses
import com.techmania.shreebhagavadgita.view.adapter.AdapterSavedVerses
import com.techmania.shreebhagavadgita.viewmodel.MainViewModel

class savedVersesFragment : Fragment() {
    private lateinit var binding: FragmentSavedVersesBinding
    private lateinit var adapterSavedVerses: AdapterSavedVerses
    private val viewModel : MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedVersesBinding.inflate(layoutInflater)
        getVerseFromRoom()

        return binding.root
    }

    private fun getVerseFromRoom() {
        viewModel.getAllVerse().observe(viewLifecycleOwner){


            adapterSavedVerses = AdapterSavedVerses(::onVersesItemVClicked)

            binding.rvVerses.adapter = adapterSavedVerses
            adapterSavedVerses.differ.submitList(it)
            binding.shimmer.visibility = View.GONE
        }
    }

    fun onVersesItemVClicked(verse: SavedVerses){
            val bundle = Bundle()
        bundle.putInt("chapterNum",verse.chapter_number)
        bundle.putInt("verseNum",verse.verse_number)
        bundle.putBoolean("showRoomData",true)
        findNavController().navigate(R.id.action_savedVersesFragment_to_versesDetailFragment,bundle)
    }


}