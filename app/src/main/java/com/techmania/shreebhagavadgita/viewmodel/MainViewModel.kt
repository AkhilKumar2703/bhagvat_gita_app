package com.techmania.shreebhagavadgita.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.techmania.shreebhagavadgita.datasource.api.room.AppDatabase
import com.techmania.shreebhagavadgita.datasource.api.room.SavedChapters
import com.techmania.shreebhagavadgita.datasource.api.room.SavedVerses
import com.techmania.shreebhagavadgita.models.ChaptersItem
import com.techmania.shreebhagavadgita.models.VersesItem
import com.techmania.shreebhagavadgita.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application): AndroidViewModel(application) {
    val savedChaptersDao=AppDatabase.getDatabaseInstance(application)?.savedChapterDao()
    val savedVersesDao=AppDatabase.getDatabaseInstance(application)?.savedVersesDao()
    val appRepository = AppRepository(savedChaptersDao!!, savedVersesDao!!)
    fun getAllChapter() : Flow<List<ChaptersItem>> = appRepository.getAllChapter()
    fun getVerses(chapterNumber : Int) : Flow<List<VersesItem>> = appRepository.getVerses(chapterNumber)

    fun getAParticularVerse(chapterNumber: Int,verseNumber: Int): Flow<VersesItem> = appRepository.getAParticularVerse(chapterNumber,verseNumber)

    // saved chapters
    suspend fun insertChapters(savedChapters: SavedChapters)= appRepository.insertChapters(savedChapters)
    fun getSavedChapter(): LiveData<List<SavedChapters>> = appRepository.getSavedChapter()
    fun getAParticularChapter(chapter_number : Int) : LiveData<SavedChapters> = appRepository.getAParticularChapter(chapter_number)
    suspend fun deleteChapter(id :Int) = appRepository.deleteChapter(id)


    // saved verse
    suspend fun deleteVerse(chapterNumber : Int,verseNumber : Int) =  appRepository.deleteVerse(chapterNumber,verseNumber)
    fun getParticularVerse(chapterNumber : Int,verseNumber : Int) = appRepository.getParticularVerse(chapterNumber, verseNumber)
    fun getAllVerse(): LiveData<List<SavedVerses>> =  appRepository.getAllVerse()
    suspend fun insertVerse(verses: SavedVerses)  = appRepository.insertVerse(verses)

}