package com.app_sample_coroutines.utils

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 　Created by Timmy on 2018/10/17.
 */

class ServiceGenerator {

    companion object {

        private val mBuilder = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())

        fun <S> createService(serviceClass: Class<S>): S {
            val builder = OkHttpClient.Builder()
            initHttpClient(builder)
            initHttpClientHeader(builder)
//            initHttpClientLog(builder)
            val client = builder.build()
            val retrofit = mBuilder.client(client).build()
            return retrofit.create(serviceClass)
        }

        private fun initHttpClient(builder: OkHttpClient.Builder) {
            val time = 10000
            builder.connectTimeout(time.toLong(), TimeUnit.MILLISECONDS)
            builder.readTimeout(time.toLong(), TimeUnit.MILLISECONDS)
            builder.writeTimeout(time.toLong(), TimeUnit.MILLISECONDS)
        }

        private fun initHttpClientHeader(builder: OkHttpClient.Builder) {
            builder.addInterceptor { chain -> chain.proceed(getHeaderRequest(chain.request())) }
        }

        private fun initHttpClientLog(builder: OkHttpClient.Builder) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        private fun getHeaderRequest(originalRequest: Request): Request {
            val requestBuilder: Request.Builder
            requestBuilder = originalRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(originalRequest.method(), originalRequest.body())
            return requestBuilder.build()
        }
    }
}