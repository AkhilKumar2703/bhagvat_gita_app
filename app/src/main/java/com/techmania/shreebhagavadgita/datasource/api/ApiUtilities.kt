package com.techmania.shreebhagavadgita.datasource.api

import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiUtilities {


    val headers = mapOf<String,String>(

        "Accept" to "application/json",
        "x-rapidapi-key" to "03e483d3edmsh7ce25245dec0863p13d4cajsn39b0e5c99d60",
        "x-rapidapi-host" to "bhagavad-gita3.p.rapidapi.com"


    )


    val client = OkHttpClient.Builder().apply {
        addInterceptor {chain ->
        val newRequest = chain.request().newBuilder().apply { 
            headers.forEach { key, value -> addHeader(key,value)  }
        }.build()
           chain.proceed(newRequest)
        }
    }.build()



    val api : ApiInterface by lazy {

        Retrofit.Builder()
            .baseUrl("https://bhagavad-gita3.p.rapidapi.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}