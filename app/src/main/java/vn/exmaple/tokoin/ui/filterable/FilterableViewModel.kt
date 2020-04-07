package vn.exmaple.tokoin.ui.filterable

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.paging.toLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import org.akd.muxic.data.local.AppDatabase
import vn.exmaple.tokoin.data.local.KeywordBoundaryCallback
import vn.exmaple.tokoin.data.local.TopHeadlineBoundaryCallback
import vn.exmaple.tokoin.data.remote.INewsRepository
import vn.exmaple.tokoin.model.Article

class FilterableViewModel(
    application: Application,
    dao: AppDatabase,
    repository: INewsRepository
) : AndroidViewModel(application) {
    private val mHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    private val mCallback = KeywordBoundaryCallback(
        repository = repository,
        dao = dao,
        scope = viewModelScope,
        handler = mHandler,
        keyword = "bitcoin"
    )
    val mTopHeadlineLive: LiveData<PagedList<Article>> = dao.article.getAll("bitcoin")
        .toLiveData(
            pageSize = 20,
            boundaryCallback = mCallback
        )

    val mStateLive: LiveData<Int>
        get() = mCallback.mState

    val mNewArticleLive: LiveData<Int>
        get() = mCallback.mNewArticle
}