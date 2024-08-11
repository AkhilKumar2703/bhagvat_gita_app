package com.techmania.shreebhagavadgita

import android.annotation.SuppressLint
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
import com.techmania.shreebhagavadgita.databinding.FragmentVersesDetailBinding
import com.techmania.shreebhagavadgita.models.Commentary
import com.techmania.shreebhagavadgita.models.Translation
import com.techmania.shreebhagavadgita.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class versesDetailFragment : Fragment() {
    private lateinit   var binding : FragmentVersesDetailBinding
    private  val viewModel : MainViewModel by viewModels()
    private var chapterNum = 0
    private var verseNum = 0
     @SuppressLint("SuspiciousIndentation")
     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentVersesDetailBinding.inflate(layoutInflater)
         changeStatusBarColour()
         getAndSetChapAndVerseNum()
         onReadMoreClicked()
         getVerseDetail()
         checkInternet()
         return binding.root
    }


    private fun checkInternet() {
        val networkManager = NetworkManager(requireContext())
        networkManager.observe(viewLifecycleOwner){
            if(it == true){
//                binding.shimmer.visibility =View.VISIBLE
//                binding.rvVerses.visibility = View.VISIBLE
                binding.tvShowingMessage.visibility =View.GONE
                binding.iv.visibility =View.GONE
                binding.progressbar.visibility =View.VISIBLE
                getVerseDetail()
            }else{
              binding.progressbar.visibility =View.GONE
                binding.tvShowingMessage.visibility =View.VISIBLE
                binding.iv.visibility =View.VISIBLE


                binding.tvVerseNumber.visibility =View.GONE
                binding.tvVerseText.visibility =View.GONE
                binding.tvTransliterationIfEnglish.visibility =View.GONE
                binding.tvWordIfEnglish.visibility =View.GONE
                binding.view.visibility =View.GONE
                binding.tvTranslation.visibility =View.GONE
                binding.clTranslation.visibility =View.GONE
                binding.tvCommentries.visibility =View.GONE
                binding.clCommentries.visibility =View.GONE
                binding.backgroundImage.visibility =View.GONE
                binding.ivFavoriteVerse.visibility =View.GONE
            }
        }
    }



    private fun onReadMoreClicked() {
        var isExpanded = false
        binding.tvSeeMore.setOnClickListener {
            if (!isExpanded) {
                binding.tvTextCommentary.maxLines = 100
                isExpanded = true
                binding.tvSeeMore.text="Read Less"
            } else {
                binding.tvTextCommentary.maxLines = 4
                isExpanded = false
                binding.tvSeeMore.text="Read More..."
            }
        }
    }

    private fun getVerseDetail() {


        lifecycleScope.launch {
            viewModel.getAParticularVerse(chapterNum,verseNum).collect{verse ->
                binding.tvVerseText.text = verse.text
                binding.tvTransliterationIfEnglish.text = verse.transliteration
                binding.tvWordIfEnglish.text = verse.word_meanings

                val hindiTranslationList = arrayListOf<Translation>()
                for (i in verse.translations){
                    if (i.language == "hindi" || i.language == "english"){
                        hindiTranslationList.add(i)

                    }
                }
                val hindiTranslationListSize = hindiTranslationList.size
                if(hindiTranslationList.isNotEmpty()){
                    binding.tvAuthorName.text=hindiTranslationList[0].author_name
                    binding.tvText.text= hindiTranslationList[0].description
                    if (hindiTranslationListSize==1) binding.fabTranslationRight.visibility = View.GONE

                    var i =0
                    binding.fabTranslationRight.setOnClickListener {
                        if(i < hindiTranslationListSize-1){
                            i++
                            binding.tvAuthorName.text=hindiTranslationList[i].author_name
                            binding.tvText.text= hindiTranslationList[i].description
                            binding.fabTranslationLeft.visibility =View.VISIBLE

                            if(i == hindiTranslationListSize-1)binding.fabTranslationRight.visibility =View.GONE

                        }
                    }

                    binding.fabTranslationLeft.setOnClickListener {
                        if(i>0){
                            i--
                            binding.tvAuthorName.text=hindiTranslationList[i].author_name
                            binding.tvText.text= hindiTranslationList[i].description
                            binding.fabTranslationRight.visibility =View.VISIBLE
                            if(i == 0)binding.fabTranslationLeft.visibility =View.GONE
                        }
                    }

                }


                val commentaryList = arrayListOf<Commentary>()
                for (i in verse.commentaries){
                    if (i.language == "hindi" || i.language == "english"){
                        commentaryList.add(i)

                    }
                }
                val commentaryListSize = commentaryList.size
                if (commentaryList.isNotEmpty()){
                    binding.tvAuthorCommentary.text=commentaryList[0].author_name
                    binding.tvTextCommentary.text= commentaryList[0].description
                    if (commentaryListSize==1) binding.fabCommentaryRight.visibility = View.GONE

                    var i =0
                    binding.fabCommentaryRight.setOnClickListener {
                        if(i < commentaryListSize-1){
                            i++
                            binding.tvAuthorNameCommentary.text=commentaryList[i].author_name
                            binding.tvTextCommentary.text= commentaryList[i].description
                            binding.fabCommentaryLeft.visibility =View.VISIBLE

                            if(i == commentaryListSize-1)binding.fabCommentaryRight.visibility =View.GONE

                        }
                    }

                    binding.fabCommentaryLeft.setOnClickListener {
                        if(i>0){
                            i--
                            binding.tvAuthorCommentary.text=commentaryList[i].author_name
                            binding.tvTextCommentary.text= commentaryList[i].description
                            binding.fabCommentaryRight.visibility =View.VISIBLE
                            if(i == 0)binding.fabCommentaryLeft.visibility =View.GONE
                        }
                    }



                }

            }
            binding.progressbar.visibility =View.GONE

            binding.tvVerseNumber.visibility =View.VISIBLE
            binding.tvVerseText.visibility =View.VISIBLE
            binding.tvTransliterationIfEnglish.visibility =View.VISIBLE
            binding.tvWordIfEnglish.visibility =View.VISIBLE
            binding.view.visibility =View.VISIBLE
            binding.tvTranslation.visibility =View.VISIBLE
            binding.clTranslation.visibility =View.VISIBLE
            binding.tvCommentries.visibility =View.VISIBLE
            binding.clCommentries.visibility =View.VISIBLE
            binding.backgroundImage.visibility =View.VISIBLE
            binding.ivFavoriteVerse.visibility =View.VISIBLE

        }

    }

    private fun getAndSetChapAndVerseNum() {
        val bundle = arguments
        chapterNum = bundle!!.getInt("chapterNum")!!
        verseNum = bundle!!.getInt("verseNum")!!
        binding.tvVerseNumber.text = "||$chapterNum.$verseNum||"


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