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
import androidx.navigation.fragment.findNavController
import com.techmania.shreebhagavadgita.databinding.FragmentSavedChaptersBinding
import com.techmania.shreebhagavadgita.models.ChaptersItem
import com.techmania.shreebhagavadgita.view.adapter.AdapterChapters
import com.techmania.shreebhagavadgita.viewmodel.MainViewModel


class savedChaptersFragment : Fragment() {
    private lateinit var binding: FragmentSavedChaptersBinding
    private lateinit var adapterChapters: AdapterChapters
    private  val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSavedChaptersBinding.inflate(layoutInflater)
        changeStatusBarColour()
        getSavedChapter()
        return binding.root
    }

    private fun getSavedChapter() {
        viewModel.getSavedChapter().observe(viewLifecycleOwner){
          val chapterList = arrayListOf<ChaptersItem>()
            for (i in it ){
                val chaptersItem = ChaptersItem(
                    i.chapter_number,
                    i.chapter_summary,
                    i.chapter_summary_hindi,
                    i.id,
                    i.name,
                    i.name_meaning,
                    i.name_translated,
                    i.name_transliterated,
                    i.slug,
                    i.verses_count
                )
                chapterList.add(chaptersItem)
            }
            if(chapterList.isNotEmpty()){
                binding.shimmer.visibility = View.GONE
                binding.rvChapters.visibility = View.GONE
                binding.tvShowingMessage.visibility = View.VISIBLE
            }
            adapterChapters = AdapterChapters(::onChapterItemViewClicked,::onFavClicked)
            binding.rvChapters.adapter = adapterChapters
            adapterChapters.differ.submitList(chapterList)


            binding.shimmer.visibility = View.GONE
            binding.rvChapters.visibility = View.VISIBLE
        }
    }


    fun onChapterItemViewClicked(chaptersItem: ChaptersItem){
        val  bundle = Bundle()
        bundle.putInt("chapterNumber",chaptersItem.chapter_number)
        bundle.putBoolean("showRoomData",true)
        findNavController().navigate(R.id.action_savedChaptersFragment_to_versesFragment,bundle)
    }

    fun onFavClicked(chaptersItem: ChaptersItem){

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