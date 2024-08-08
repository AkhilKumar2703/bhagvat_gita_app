package com.techmania.shreebhagavadgita.viewmodel

import androidx.lifecycle.ViewModel
import com.techmania.shreebhagavadgita.models.ChaptersItem
import com.techmania.shreebhagavadgita.models.VersesItem
import com.techmania.shreebhagavadgita.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel: ViewModel() {
    val appRepository = AppRepository()
    fun getAllChapter() : Flow<List<ChaptersItem>> = appRepository.getAllChapter()
    fun getVerses(chapterNumber : Int) : Flow<List<VersesItem>> = appRepository.getVerses(chapterNumber)
}