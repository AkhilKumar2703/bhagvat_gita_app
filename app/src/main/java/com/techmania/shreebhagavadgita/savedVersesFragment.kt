package com.techmania.shreebhagavadgita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.techmania.shreebhagavadgita.databinding.FragmentSavedVersesBinding
import com.techmania.shreebhagavadgita.view.adapter.AdapterVerses
import com.techmania.shreebhagavadgita.viewmodel.MainViewModel

class savedVersesFragment : Fragment() {
    private lateinit var binding: FragmentSavedVersesBinding
    private lateinit var adapterVerses: AdapterVerses
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
            val verseList = arrayListOf<String>()

            for (savedVerse in it){

            verseList.add(savedVerse.text[0].toString())
            }

            if (verseList.isEmpty()){
                binding.tvShowingMessage.visibility = View.VISIBLE
            }

            adapterVerses = AdapterVerses(::onVersesItemVClicked)

            binding.rvVerses.adapter = adapterVerses
            adapterVerses.differ.submitList(verseList)
            binding.shimmer.visibility = View.GONE
        }
    }

    fun onVersesItemVClicked(verse: String,verNumber: Int){
            val bundle = Bundle()

        bundle.putBoolean("showRoomData",true)
        findNavController().navigate(R.id.action_savedVersesFragment_to_versesDetailFragment,bundle)
    }


}