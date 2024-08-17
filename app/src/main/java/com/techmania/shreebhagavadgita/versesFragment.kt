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
        getData()



        return binding.root
    }

    private fun getData() {
        val  bundle = arguments
        val showDataFromRoom =  bundle?.getBoolean("showRoomData",false)

        if (showDataFromRoom == true){

            getDataFromRoom()
        }else{
            checkInternet()
        }
    }

    private fun getDataFromRoom() {

        viewModel.getAParticularChapter(chapterNumber).observe(viewLifecycleOwner){
            binding.tvChapterNumber.text = "Chapter ${it.chapter_number}"
            binding.tvChapterTitle.text = it.name_translated
            binding.tvChapterDescription.text = it.chapter_summary
            binding.tvChapterDescriptionHindi.text = it.chapter_summary_hindi
            binding.tvNumberOfVerses.text = it.verses_count.toString()

            showListInAdapter(it.verses)

        }
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
        val bundle = Bundle()
        bundle.putInt("chapterNum",chapterNumber)
        bundle.putInt("verseNum",verseNumber)
findNavController().navigate(R.id.action_versesFragment_to_versesDetailFragment,bundle)
    }

    private fun onReadMoreClicked() {
        var isExpanded = false
        binding.tvSeeMore.setOnClickListener {
            if(!isExpanded){
                binding.tvChapterDescription.maxLines = 50
                isExpanded = true
                binding.tvSeeMore.text="Read Less"
            }else{
                binding.tvChapterDescription.maxLines = 4
                isExpanded = false
                binding.tvSeeMore.text="Read More..."
            }
        }
        binding.tvSeeMoreHindi.setOnClickListener {
            if(!isExpanded){
                binding.tvChapterDescriptionHindi.maxLines = 50
                isExpanded = true
                binding.tvSeeMoreHindi.text="Read Less"
            }else{
                binding.tvChapterDescriptionHindi.maxLines = 4
                isExpanded = false
                binding.tvSeeMoreHindi.text="Read More..."
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

                val verseList  = arrayListOf<String>()

                for (currentVerse in it) {
                   for (verses in currentVerse.translations) {
                       if(verses.language == "hindi"){
                           verseList.add(verses.description)
                           break
                       }
                   }
                }

                showListInAdapter(verseList)


            }
        }

    }

    private fun showListInAdapter(verseList: List<String>) {
        adapterVerses = AdapterVerses(::onVersesItemVClicked)
        binding.rvVerses.adapter = adapterVerses
        adapterVerses.differ.submitList(verseList)
        binding.shimmer.visibility = View.GONE
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