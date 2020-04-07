package vn.exmaple.tokoin.model

data class NewsResponse(
    val status: String? = null,
    val statusResult: String? = null,
    var articles: List<Article> = mutableListOf()
)