package com.app_sample_coroutines.api.taipei.service

import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.GET

/**
 * 　Created by Timmy on 2018/10/17.
 */

interface TaipeiService {
    @GET("posts")
    fun getCarPark(): Call<Any>

    @GET("posts")
    fun getCarParkByCoroutine(): Deferred<Any>
}