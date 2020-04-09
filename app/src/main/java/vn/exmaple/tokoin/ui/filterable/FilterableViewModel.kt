package vn.exmaple.tokoin.ui.filterable

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.paging.toLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.akd.muxic.data.local.AppDatabase
import org.akd.support.extensions.mutableLiveDataOf
import org.akd.support.util.preference.SharePrefUtil
import vn.exmaple.tokoin.common.Constant
import vn.exmaple.tokoin.data.local.KeywordBoundaryCallback
import vn.exmaple.tokoin.data.remote.INewsRepository
import vn.exmaple.tokoin.model.Account
import vn.exmaple.tokoin.model.Article


class FilterableViewModel(
    application: Application,
    dao: AppDatabase,
    repository: INewsRepository
) : AndroidViewModel(application) {
    private val mSharePref: SharePrefUtil = SharePrefUtil.with(application.applicationContext).ok()
    private val mHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    val mStateLive: MutableLiveData<Int> = mutableLiveDataOf()
    var mFilterTextLive = mutableLiveDataOf<String>()
    val mTopHeadlineLive: LiveData<PagedList<Article>> =
        Transformations.switchMap(mFilterTextLive) {
            val callback = KeywordBoundaryCallback(
                repository = repository,
                dao = dao,
                scope = viewModelScope,
                handler = mHandler,
                state = mStateLive,
                keyword = it
            )
            val config = PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(20 * 2)
                .setEnablePlaceholders(false)
                .build()
            return@switchMap dao.article.getAll(it)
                .toLiveData(
                    config = config,
                    boundaryCallback = callback
                )
        }

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