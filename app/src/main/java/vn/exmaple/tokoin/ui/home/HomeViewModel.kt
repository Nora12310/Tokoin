package vn.exmaple.tokoin.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineExceptionHandler
import org.akd.muxic.data.local.AppDatabase
import vn.exmaple.tokoin.common.Constant
import vn.exmaple.tokoin.data.local.TopHeadlineBoundaryCallback
import vn.exmaple.tokoin.data.remote.INewsRepository

class HomeViewModel(
    application: Application,
    dao: AppDatabase,
    repository: INewsRepository
) : AndroidViewModel(application) {
    private val mHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        mStateLive.value = TopHeadlineBoundaryCallback.DONE
    }
    private val mCallback = TopHeadlineBoundaryCallback(
        repository = repository,
        dao = dao,
        scope = viewModelScope,
        handler = mHandler
    )
    private val factory = dao.article.getAll(Constant.TOP_HEADlINNE)
    private val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setInitialLoadSizeHint(20 * 2)
        .setEnablePlaceholders(false)
        .build()
    val mTopHeadlineLive = LivePagedListBuilder(factory, config)
        .setBoundaryCallback(mCallback)
        .build()

    val mStateLive: MutableLiveData<Int>
        get() = mCallback.mState

    val mNewArticleLive: LiveData<Int>
        get() = mCallback.mNewArticle
}