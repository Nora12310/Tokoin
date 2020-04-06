package vn.exmaple.tokoin.data.local

import androidx.paging.PagedList
import kotlinx.coroutines.*
import org.akd.muxic.data.local.AppDatabase
import org.akd.support.helper.AppExecutors
import org.akd.support.helper.PagingRequestHelper
import org.akd.support.helper.PagingRequestHelper.RequestType.INITIAL
import vn.exmaple.tokoin.data.remote.INewsRepository
import vn.exmaple.tokoin.data.remote.request.TopHeadlineRequest
import vn.exmaple.tokoin.model.Article

class ArticleBoundaryCallback(
    private val repository: INewsRepository,
    private val dao: AppDatabase,
    private val scope: CoroutineScope,
    private val handler: CoroutineExceptionHandler
) : PagedList.BoundaryCallback<Article>() {
    private val helper = PagingRequestHelper(AppExecutors.diskIO)

    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(INITIAL) {
            scope.launch(handler) {
                val request = TopHeadlineRequest("us")
                val items = withContext(Dispatchers.IO) { repository.getHeadlineNews(request) }
                dao.article.save(*items.toTypedArray())
                it.recordSuccess()
            }
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: Article) {
        super.onItemAtFrontLoaded(itemAtFront)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Article) {
        super.onItemAtEndLoaded(itemAtEnd)
    }
}