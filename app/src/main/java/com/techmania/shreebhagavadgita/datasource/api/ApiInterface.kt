package com.techmania.shreebhagavadgita.datasource.api

import com.techmania.shreebhagavadgita.models.ChaptersItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {
    @Headers(
        "Accept: application/json",
        "x-rapidapi-key: 03e483d3edmsh7ce25245dec0863p13d4cajsn39b0e5c99d60",
    "x-rapidapi-host: bhagavad-gita3.p.rapidapi.com"
    )
    @GET("/v2/chapters/")
    fun getAllChapters() : Call<List<ChaptersItem>>
}