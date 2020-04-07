package vn.exmaple.tokoin.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import org.akd.muxic.data.local.AppDatabase
import vn.exmaple.tokoin.model.Account

class ProfileViewModel(
    application: Application,
    dao: AppDatabase
) : AndroidViewModel(application) {

    val mProfilesLive: LiveData<PagedList<Account>> = dao.account.getAll().toLiveData(pageSize = 20)
}