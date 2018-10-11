package com.app_sample_coroutines.api.pojo

import com.google.gson.annotations.SerializedName

/**
 * Created by Louis on 2018/10/11.
 */
data class SampleData(@SerializedName("query") val queryData: QueryData) {
}

data class QueryData(@SerializedName("count") val count: Int,
                     @SerializedName("created") val created: String,
                     @SerializedName("lang") val lang: String) {
}