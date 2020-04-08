package vn.exmaple.tokoin.data.local

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import kotlinx.coroutines.*
import org.akd.muxic.data.local.AppDatabase
import org.akd.support.helper.AppExecutors
import org.akd.support.helper.PagingRequestHelper
import org.akd.support.helper.PagingRequestHelper.RequestType.AFTER
import org.akd.support.helper.PagingRequestHelper.RequestType.INITIAL
import vn.exmaple.tokoin.data.remote.INewsRepository
import vn.exmaple.tokoin.data.remote.request.NewsRequest
import vn.exmaple.tokoin.model.Article

class KeywordBoundaryCallback(
    private val repository: INewsRepository,
    private val dao: AppDatabase,
    private val scope: CoroutineScope,
    private val handler: CoroutineExceptionHandler,
    private val state: MutableLiveData<Int>,
    private val keyword: String = ""
) : PagedList.BoundaryCallback<Article>() {
    private val helper = PagingRequestHelper(AppExecutors.diskIO)
    private var page = 1

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        helper.runIfNotRunning(INITIAL) {
            scope.launch(handler) {
                state.value = LOADING
                val request = NewsRequest(keyword = keyword)
                val items = repository.getEverything(request)
                dao.article.save(*items.toTypedArray())
                state.value = DONE
            }
            it.recordSuccess()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Article) {
        helper.runIfNotRunning(AFTER) {
            scope.launch(handler) {
                val count = withContext(Dispatchers.IO) {
                    dao.article.count(keyword)
                }
                val nextPage = (count / 20) + 1
                if (nextPage == page) return@launch
                page = nextPage
                val request = NewsRequest(
                    keyword = keyword,
                    page = nextPage,
                    pageSize = 20
                )
                val items = withContext(Dispatchers.IO) { repository.getEverything(request) }
                dao.article.save(*items.toTypedArray())
            }
            it.recordSuccess()
        }
    }

    companion object {
        const val LOADING = 1
        const val DONE = 2
    }
}