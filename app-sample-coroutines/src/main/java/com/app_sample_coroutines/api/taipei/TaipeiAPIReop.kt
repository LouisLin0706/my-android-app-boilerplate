package com.app_sample_coroutines.api.taipei

import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

/**
 * 　Created by Timmy on 2018/10/17.
 */

class TaipeiAPIReop {
    val apiClient = TaipeiAPIClient()

//    fun fetchCarPark() = apiClient.getCarParkByCoroutine()

    suspend fun fetchCarPark() = apiClient.getCarPark().await()
//        return suspendCoroutine { cont ->
//            Log.i("TAG", " ===== TimmyDev ===== " + " Repo 1. Thread Name : ${Thread.currentThread().name} ")
//            apiClient.getCarPark().enqueue(object : Callback<Any> {
//                override fun onFailure(call: Call<Any>, t: Throwable) {
//                    Log.i("TAG", " ===== TimmyDev ===== " + " Repo 2. Thread Name : ${Thread.currentThread().name} ")
//                    cont.resumeWithException(t)
//                }
//
//                override fun onResponse(call: Call<Any>, response: Response<Any>) {
//                    Log.i("TAG", " ===== TimmyDev ===== " + " Repo 3. Thread Name : ${Thread.currentThread().name} ")
//                    if (response.isSuccessful) {
//                        cont.resume(response.body()!!)
//                    } else {
//                        cont.resumeWithException(HttpException(response))
//                    }
//                }
//            })
//        }


    suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine { cont ->
        cont.invokeOnCancellation {
            if (cont.isCancelled) {
                cont.cancel()
            }
        }
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>?, t: Throwable) {
                cont.resumeWithException(t)
            }
            override fun onResponse(call: Call<T>?, response: Response<T>) {
                if (response.isSuccessful) {
                    val body = response.body()
                            ?: return cont.resumeWithException(NullPointerException())
                    cont.resume(body)
                } else {
                    cont.resumeWithException(HttpException(response))
                }
            }
        })
    }
}