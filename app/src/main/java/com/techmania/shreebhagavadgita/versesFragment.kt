package com.techmania.shreebhagavadgita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.techmania.shreebhagavadgita.databinding.FragmentVersesBinding
import com.techmania.shreebhagavadgita.view.adapter.AdapterVerses
import com.techmania.shreebhagavadgita.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class versesFragment : Fragment() {

private lateinit var  binding : FragmentVersesBinding
private lateinit var adapterVerses: AdapterVerses
private var chapterNumber = 0
private val viewModel : MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding =FragmentVersesBinding.inflate(layoutInflater)
        changeStatusBarColour()
        onReadMoreClicked()
        getAndSetChapterDetail()
        checkInternet()


        return binding.root
    }

    private fun checkInternet() {
        val networkManager = NetworkManager(requireContext())
        networkManager.observe(viewLifecycleOwner){
            if(it == true){
                binding.shimmer.visibility =View.VISIBLE
                binding.rvVerses.visibility = View.VISIBLE
                binding.tvShowingMessage.visibility =View.GONE
                getAllVerses()
            }else{
                binding.shimmer.visibility =View.GONE
                binding.rvVerses.visibility = View.GONE
                binding.tvShowingMessage.visibility =View.VISIBLE
            }
        }
    }

    private fun onVersesItemVClicked(verse:String,verseNumber : Int){
findNavController().navigate(R.id.action_versesFragment_to_versesDetailFragment)
    }

    private fun onReadMoreClicked() {
        var isExpanded = false
        binding.tvSeeMore.setOnClickListener {
            if(!isExpanded){
                binding.tvChapterDescription.maxLines = 50
                isExpanded = true
            }else{
                binding.tvChapterDescription.maxLines = 4
                isExpanded = false
            }
        }
        binding.tvSeeMoreHindi.setOnClickListener {
            if(!isExpanded){
                binding.tvChapterDescriptionHindi.maxLines = 50
                isExpanded = true
            }else{
                binding.tvChapterDescriptionHindi.maxLines = 4
                isExpanded = false
            }
        }
    }

    private fun getAndSetChapterDetail() {
        val bundle = arguments
        chapterNumber = bundle?.getInt("chapterNumber")!!
        binding.tvChapterNumber.text = "Chapter ${bundle?.getInt("chapterNumber")}"
        binding.tvChapterTitle.text = bundle?.getString("chapterTitle")
        binding.tvChapterDescription.text = bundle?.getString("chapterDesc")
        binding.tvChapterDescriptionHindi.text = bundle?.getString("chapterDescHindi")
        binding.tvNumberOfVerses.text = bundle?.getInt("verseCount").toString()

    }

    private fun getAllVerses() {
        lifecycleScope.launch {
            viewModel.getVerses(chapterNumber).collect{
                adapterVerses = AdapterVerses(::onVersesItemVClicked)
                binding.rvVerses.adapter = adapterVerses
                val verseList  = arrayListOf<String>()

                for (currentVerse in it) {
                   for (verses in currentVerse.translations) {
                       if(verses.language == "hindi"){
                           verseList.add(verses.description)
                           break
                       }
                   }
                }


                adapterVerses.differ.submitList(verseList)

                binding.shimmer.visibility = View.GONE

            }
        }

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