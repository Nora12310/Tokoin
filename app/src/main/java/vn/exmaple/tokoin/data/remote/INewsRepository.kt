package vn.exmaple.tokoin.data.remote

import com.annimon.stream.Stream
import org.akd.support.extensions.asMap
import vn.exmaple.tokoin.common.Constant
import vn.exmaple.tokoin.data.remote.request.NewsRequest
import vn.exmaple.tokoin.model.Article

interface INewsRepository {
    suspend fun getHeadlineNews(request: NewsRequest): List<Article>
    suspend fun getEverything(request: NewsRequest): List<Article>
}

class NewsRepositoryImpl(private val service: NewsAPIGenerator) : INewsRepository {
    override suspend fun getHeadlineNews(request: NewsRequest): List<Article> {
        val response = service.api.getTopHeadline(request.asMap())
        return Stream.of(response.articles).map {
            it.keyword = Constant.TOP_HEADlINNE
            return@map it
        }.toList()
    }

    override suspend fun getEverything(request: NewsRequest): List<Article> {
        val response = service.api.getEverything(request.asMap())
        return Stream.of(response.articles).map {
            it.keyword = request.keyword
            return@map it
        }.toList()
    }

}

