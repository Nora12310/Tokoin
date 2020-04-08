package vn.exmaple.tokoin.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
import vn.exmaple.tokoin.data.local.TopHeadlineBoundaryCallback
import vn.exmaple.tokoin.model.Account

class ProfileViewModel(
    application: Application,
    dao: AppDatabase
) : AndroidViewModel(application) {
    private val mSharePref: SharePrefUtil = SharePrefUtil.with(application.applicationContext).ok()
    val mProfilesLive: LiveData<PagedList<Account>> = dao.account.getAll().toLiveData(pageSize = 20)
    private val mHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
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