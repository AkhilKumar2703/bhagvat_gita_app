package com.techmania.shreebhagavadgita.repository

import com.techmania.shreebhagavadgita.datasource.api.ApiUtilities
import com.techmania.shreebhagavadgita.models.ChaptersItem
import com.techmania.shreebhagavadgita.models.VersesItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppRepository {

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
}