package vn.exmaple.tokoin.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.akd.muxic.data.local.AppDatabase
import org.akd.support.extensions.mutableLiveDataOf
import org.akd.support.util.preference.SharePrefUtil
import vn.exmaple.tokoin.common.Constant
import vn.exmaple.tokoin.data.local.TopHeadlineBoundaryCallback
import vn.exmaple.tokoin.data.remote.INewsRepository
import vn.exmaple.tokoin.model.Account

class HomeViewModel(
    application: Application,
    dao: AppDatabase,
    repository: INewsRepository
) : AndroidViewModel(application) {
    private val mSharePref: SharePrefUtil = SharePrefUtil.with(application.applicationContext).ok()
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

    val mAccountIsActiveLive: LiveData<Account> =
        mSharePref.readIntPref(Constant.ACCOUNT_ID, 0).let {
            val liveData: MutableLiveData<Account> = mutableLiveDataOf()
            viewModelScope.launch(mHandler) {
                val account = withContext(Dispatchers.IO) { dao.account.get(it) }
                account?.apply { liveData.value = this }
            }
            return@let liveData
        }
}