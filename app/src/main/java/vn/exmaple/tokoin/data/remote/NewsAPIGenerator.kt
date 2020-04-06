package vn.exmaple.tokoin.data.remote

import android.content.Context
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.akd.support.data.domain.APIGenerator
import retrofit2.http.GET
import retrofit2.http.QueryMap
import vn.exmaple.tokoin.model.TopHeadline

class NewsAPIGenerator(context: Context) : APIGenerator(
    context,
    URL
) {
    private val cache: Cache = Cache(context.cacheDir, CACHE_SIZE)
    val api: APILink = createService(APILink::class.java)

    override fun setMoshiConfig(): Moshi {
        val moshi = super.setMoshiConfig().newBuilder()
        moshi.add(DateFormatFactory())
        return moshi.build()
    }

    override fun addOkHttpBuilder(): OkHttpClient.Builder {
        val builder = super.addOkHttpBuilder()
        builder.cache(cache).addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
                .build()
            return@addInterceptor it.proceed(request)
        }
        return builder
    }

    companion object {
        const val URL = "https://newsapi.org/v2/"
        const val API_KEY = "97015c253f5e42de93ca1c5210772a39"
        private const val CACHE_SIZE = 10 * 1024 * 1024L
    }

    interface APILink {
        @GET("top-headlines")
        suspend fun getTopHeadline(@QueryMap maps: Map<String, String>): TopHeadline
    }
}