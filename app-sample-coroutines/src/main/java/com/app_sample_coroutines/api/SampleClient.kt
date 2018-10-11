package com.app_sample_coroutines.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Louis on 2018/10/11.
 */
class SampleClient {
    public var service: SampleService
    init {
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create()
        val client = OkHttpClient.Builder()
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://query.yahooapis.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        service = retrofit.create(SampleService::class.java)
    }
}