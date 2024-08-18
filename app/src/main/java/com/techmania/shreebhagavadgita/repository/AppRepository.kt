package com.techmania.shreebhagavadgita.repository

import androidx.lifecycle.LiveData
import com.techmania.shreebhagavadgita.datasource.api.ApiUtilities
import com.techmania.shreebhagavadgita.datasource.api.room.SavedChapters
import com.techmania.shreebhagavadgita.datasource.api.room.SavedChaptersDao
import com.techmania.shreebhagavadgita.datasource.api.room.SavedVerses
import com.techmania.shreebhagavadgita.datasource.api.room.SavedVersesDao
import com.techmania.shreebhagavadgita.models.ChaptersItem
import com.techmania.shreebhagavadgita.models.VersesItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppRepository(val savedChaptersDao: SavedChaptersDao,val savedVersesDao: SavedVersesDao) {

    fun getAllChapter() : Flow<List<ChaptersItem>> = callbackFlow {
        val callback =object  : Callback<List<ChaptersItem>>{
            override fun onResponse(
                call: Call<List<ChaptersItem>>,
                response: Response<List<ChaptersItem>>
            ) {
                if (response.isSuccessful && response.body()!= null){
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<List<ChaptersItem>>, t: Throwable) {
                  close(t)
            }

        }

        ApiUtilities.api.getAllChapters().enqueue(callback)
        awaitClose {  }


    }
    fun getVerses(chapterNumber : Int) : Flow<List<VersesItem>> = callbackFlow {
        val callback = object : Callback<List<VersesItem>>{
            override fun onResponse(
                call: Call<List<VersesItem>>,
                response: Response<List<VersesItem>>
            ) {
                if (response.isSuccessful && response.body()!= null){
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<List<VersesItem>>, t: Throwable) {
                close(t)
            }

        }
        ApiUtilities.api.getVerses(chapterNumber).enqueue(callback)
        awaitClose {  }
    }

    fun getAParticularVerse(chapterNumber: Int,verseNumber: Int): Flow<VersesItem> = callbackFlow {
        val callback = object : Callback<VersesItem>{
            override fun onResponse(call: Call<VersesItem>, response: Response<VersesItem>) {
                if (response.isSuccessful && response.body()!= null) {
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<VersesItem>, t: Throwable) {
                close(t)
            }

        }
        ApiUtilities.api.getAParticularVerse(chapterNumber,verseNumber).enqueue(callback)
        awaitClose {  }
    }

    // saved chapters
    suspend fun insertChapters(savedChapters: SavedChapters)= savedChaptersDao.insertChapters(savedChapters)

    fun getSavedChapter(): LiveData<List<SavedChapters>> = savedChaptersDao.getSavedChapter()
    fun getAParticularChapter(chapter_number : Int) : LiveData<SavedChapters> = savedChaptersDao.getAParticularChapter(chapter_number)

    suspend fun deleteChapter(id :Int) = savedChaptersDao.deleteChapter(id)

    //saved verses

    suspend fun deleteVerse(chapterNumber : Int,verseNumber : Int) =  savedVersesDao.deleteVerse(chapterNumber,verseNumber)
    fun getParticularVerse(chapterNumber : Int,verseNumber : Int) = savedVersesDao.getParticularVerse(chapterNumber, verseNumber)
    fun getAllVerse(): LiveData<List<SavedVerses>> =  savedVersesDao.getAllVerse()
    suspend fun insertVerse(verses: SavedVerses)  = savedVersesDao.insertVerse(verses)
}