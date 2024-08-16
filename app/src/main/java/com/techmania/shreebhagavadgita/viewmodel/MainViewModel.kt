package com.techmania.shreebhagavadgita.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.techmania.shreebhagavadgita.datasource.api.room.AppDatabase
import com.techmania.shreebhagavadgita.datasource.api.room.SavedChapters
import com.techmania.shreebhagavadgita.models.ChaptersItem
import com.techmania.shreebhagavadgita.models.VersesItem
import com.techmania.shreebhagavadgita.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application): AndroidViewModel(application) {
    val savedChaptersDao=AppDatabase.getDatabaseInstance(application)?.savedChapterDao()
    val appRepository = AppRepository(savedChaptersDao!!)
    fun getAllChapter() : Flow<List<ChaptersItem>> = appRepository.getAllChapter()
    fun getVerses(chapterNumber : Int) : Flow<List<VersesItem>> = appRepository.getVerses(chapterNumber)

    fun getAParticularVerse(chapterNumber: Int,verseNumber: Int): Flow<VersesItem> = appRepository.getAParticularVerse(chapterNumber,verseNumber)

    suspend fun insertChapters(savedChapters: SavedChapters)= appRepository.insertChapters(savedChapters)
}