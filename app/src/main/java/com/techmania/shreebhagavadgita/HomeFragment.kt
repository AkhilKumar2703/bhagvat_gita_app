package com.techmania.shreebhagavadgita

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.techmania.shreebhagavadgita.databinding.FragmentHomeBinding
import com.techmania.shreebhagavadgita.view.adapter.AdapterChapters
import com.techmania.shreebhagavadgita.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
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

        return binding.root
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
               adapterChapters = AdapterChapters()
                binding.rvChapters.adapter =adapterChapters
                adapterChapters.differ.submitList(chapterList)
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