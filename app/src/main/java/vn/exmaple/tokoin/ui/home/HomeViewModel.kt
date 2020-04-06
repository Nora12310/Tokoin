package vn.exmaple.tokoin.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import org.akd.muxic.data.local.AppDatabase
import vn.exmaple.tokoin.data.local.ArticleBoundaryCallback
import vn.exmaple.tokoin.data.remote.INewsRepository
import vn.exmaple.tokoin.model.Article

class HomeViewModel(
    application: Application,
    dao: AppDatabase,
    repository: INewsRepository
) : AndroidViewModel(application) {
    private val mHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    val mTopHeadlineLive: LiveData<PagedList<Article>> = dao.article.getAll()
        .toLiveData(
            pageSize = 50,
            boundaryCallback = ArticleBoundaryCallback(repository, dao, viewModelScope, mHandler)
        )
}