package com.app_sample_coroutines.api.taipei

import com.app_sample_coroutines.api.taipei.service.TaipeiService
import com.app_sample_coroutines.utils.ServiceGenerator
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call

/**
 * 　Created by Timmy on 2018/10/17.
 */

class TaipeiAPIClient {

    val apiService = ServiceGenerator.createService(TaipeiService::class.java)

    fun getCarPark(): Call<Any> {
        return apiService.getCarPark()
    }

    fun getCarParkByCoroutine(): Deferred<Any> {
        return apiService.getCarParkByCoroutine()
    }
}