package vn.exmaple.tokoin.data.remote

import org.akd.support.extensions.asMap
import vn.exmaple.tokoin.data.remote.request.TopHeadlineRequest
import vn.exmaple.tokoin.model.Article

interface INewsRepository {
    suspend fun getHeadlineNews(request: TopHeadlineRequest): List<Article>
}

class NewsRepositoryImpl(private val service: NewsAPIGenerator) : INewsRepository {
    override suspend fun getHeadlineNews(request: TopHeadlineRequest): List<Article> {
        val response = service.api.getTopHeadline(request.asMap())
        return response.articles
    }

}

