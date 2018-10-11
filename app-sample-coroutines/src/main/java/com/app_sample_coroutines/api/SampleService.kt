package com.app_sample_coroutines.api

import com.app_sample_coroutines.api.pojo.SampleData
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Louis on 2018/10/11.
 */
interface SampleService {
    @GET("v1/public/yql?q=Select+country+From+geo.places+Where+text+=+\"(26.351999,119.385763)\"&format=json")
    fun testCall(): Call<SampleData>
}