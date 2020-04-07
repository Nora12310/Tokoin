package vn.exmaple.tokoin.data.remote.request

import com.squareup.moshi.Json

data class NewsRequest(
    val country: String?=null,
    val pageSize: Int = 20,
    val page: Int = 1,
    @Json(name = "q") val keyword: String? = null
)