package vn.exmaple.tokoin.dialog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.akd.muxic.data.local.AppDatabase
import org.akd.support.extensions.mutableLiveDataOf
import org.akd.support.util.preference.SharePrefUtil
import vn.exmaple.tokoin.R
import vn.exmaple.tokoin.common.Constant
import vn.exmaple.tokoin.model.Account
import java.util.*

class AddProfileDialogViewModel(
    application: Application,
    private val dao: AppDatabase
) : AndroidViewModel(application) {
    private val mSharePrefUtil: SharePrefUtil = SharePrefUtil.with(application).ok()
    private val mHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    val mErrorLive: MutableLiveData<Int> = mutableLiveDataOf()
    val mExecuteLive: MutableLiveData<Account> = mutableLiveDataOf()

    fun save(username: String, keyword: String) {
        if (username.trim().isEmpty() || keyword.trim().isEmpty()) {
            mErrorLive.value = R.string.msg_username_or_keyword_empty
            return
        }
        viewModelScope.launch(mHandler) {
            val id = username.trim().toLowerCase(Locale.getDefault()).hashCode()
            val existedProfile = withContext(Dispatchers.IO) { dao.account.get(id) }
            if (existedProfile != null) {
                mErrorLive.value = R.string.msg_account_existed
                return@launch
            }
            val newAccount = Account(id, username, keyword)
            mSharePrefUtil.saveIntPref(Constant.ACCOUNT_ID, newAccount.id)
            withContext(Dispatchers.IO) { dao.account.save(newAccount) }
            mExecuteLive.value = newAccount
        }
    }
}