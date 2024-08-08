package com.techmania.shreebhagavadgita.datasource.api

import com.techmania.shreebhagavadgita.models.ChaptersItem
import com.techmania.shreebhagavadgita.models.VersesItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {

    @GET("/v2/chapters/")
    fun getAllChapters() : Call<List<ChaptersItem>>

    @GET("/v2/chapters/{chapterNumber}/verses/")
    fun getVerses(@Path("chapterNumber") chapterNumber : Int) : Call<List<VersesItem>>
}