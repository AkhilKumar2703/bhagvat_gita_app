package com.techmania.shreebhagavadgita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.techmania.shreebhagavadgita.databinding.FragmentHomeBinding
import com.techmania.shreebhagavadgita.datasource.api.room.SavedChapters
import com.techmania.shreebhagavadgita.models.ChaptersItem
import com.techmania.shreebhagavadgita.view.adapter.AdapterChapters
import com.techmania.shreebhagavadgita.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

private lateinit var binding: FragmentHomeBinding
private  val viewModel : MainViewModel by viewModels()
    private lateinit var adapterChapters: AdapterChapters
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        changeStatusBarColour()
        checkInternetConnectivity()
        getAllChapter()
        binding.ivSettings.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_settingsFragment) }
        return binding.root
    }

    private fun onFavClicked(chaptersItem: ChaptersItem){
        lifecycleScope.launch {
            viewModel.getVerses(chaptersItem.chapter_number).collect{
                val verseList  = arrayListOf<String>()
                for (currentVerse in it) {
                    for (verses in currentVerse.translations) {
                        if(verses.language == "hindi"){
                            verseList.add(verses.description)
                            break
                        }
                    }
                }

               val savedChapters = SavedChapters(
                   chapter_number = chaptersItem.chapter_number,
                   chapter_summary = chaptersItem.chapter_summary,
                   chapter_summary_hindi = chaptersItem.chapter_summary_hindi,
                   id = chaptersItem.id,
                   name = chaptersItem.name,
                   name_meaning = chaptersItem.name_meaning,
                   name_translated = chaptersItem.name_translated,
                   name_transliterated = chaptersItem.name_transliterated,
                   slug = chaptersItem.slug,
                   verses_count = chaptersItem.verses_count,
                   verses = verseList



               )
                viewModel.insertChapters(savedChapters)
            }
        }
    }


    private fun onFavFilledClicked(chaptersItem: ChaptersItem){
        lifecycleScope.launch {
            viewModel.deleteChapter(chaptersItem.id)
        }
    }



    private fun checkInternetConnectivity() {
        val networkManager = NetworkManager(requireContext())
        networkManager.observe(viewLifecycleOwner){
            if(it == true){
                binding.shimmer.visibility =View.VISIBLE
                binding.rvChapters.visibility = View.VISIBLE
                binding.tvShowingMessage.visibility =View.GONE
                getAllChapter()
            }else{
                binding.shimmer.visibility =View.GONE
                binding.rvChapters.visibility = View.GONE
                binding.tvShowingMessage.visibility =View.VISIBLE
            }
        }
    }

    private fun getAllChapter() {
        lifecycleScope.launch {
            viewModel.getAllChapter().collect{chapterList ->
               adapterChapters = AdapterChapters(::onChapterIVClicked,::onFavClicked,::onFavFilledClicked)
                binding.rvChapters.adapter =adapterChapters
                adapterChapters.differ.submitList(chapterList)
                binding.shimmer.visibility = View.GONE

            }

        }
    }

    private  fun onChapterIVClicked(chaptersItem: ChaptersItem){
        val  bundle = Bundle()
        bundle.putInt("chapterNumber",chaptersItem.chapter_number)
        bundle.putString("chapterTitle",chaptersItem.name_translated)
        bundle.putString("chapterDesc",chaptersItem.chapter_summary)
        bundle.putString("chapterDescHindi",chaptersItem.chapter_summary_hindi)
        bundle.putInt("verseCount",chaptersItem.verses_count)
findNavController().navigate(R.id.action_homeFragment_to_versesFragment,bundle)
    }

    private fun changeStatusBarColour() {
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        if (window != null) {
            WindowCompat.getInsetsController(window, window.decorView).apply {
                isAppearanceLightStatusBars = true
            }
        }
    }


}