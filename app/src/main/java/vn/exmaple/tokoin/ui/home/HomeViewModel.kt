package vn.exmaple.tokoin.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.exmaple.tokoin.data.remote.INewsRepository
import vn.exmaple.tokoin.data.remote.request.TopHeadlineRequest

class HomeViewModel(
    application: Application,
    private val repository: INewsRepository
) : AndroidViewModel(application) {
    private val mHandler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    fun getTopHeadline() {
        viewModelScope.launch(mHandler) {
            val request = TopHeadlineRequest("us")
            val items = withContext(Dispatchers.IO) { repository.getHeadlineNews(request) }
            Log.v("top-header", items.toString())
        }
    }
}