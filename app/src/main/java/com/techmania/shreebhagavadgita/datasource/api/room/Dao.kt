package com.techmania.shreebhagavadgita.datasource.api.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface SavedChaptersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(savedChapters: SavedChapters)

    @Query("SELECT * FROM SavedChapters")
   fun getSavedChapter(): LiveData<List<SavedChapters>>

   @Query("DELETE FROM SavedChapters WHERE id = :id")
   fun deleteChapter(id :Int)

   @Query("SELECT * FROM SavedChapters WHERE  chapter_number = :chapter_number")
   fun getAParticularChapter(chapter_number : Int) : LiveData<SavedChapters>

}