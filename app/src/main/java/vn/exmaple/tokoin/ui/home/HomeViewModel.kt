package vn.exmaple.tokoin.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.akd.support.extensions.mutableLiveDataOf
import vn.exmaple.tokoin.data.remote.INewsRepository
import vn.exmaple.tokoin.data.remote.request.TopHeadlineRequest
import vn.exmaple.tokoin.model.Article

class HomeViewModel(
    application: Application,
    private val repository: INewsRepository
) : AndroidViewModel(application) {
    val mTopHeadlineLive: MutableLiveData<List<Article>> = mutableLiveDataOf()
    private val mHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun getTopHeadline() {
        viewModelScope.launch(mHandler) {
            val request = TopHeadlineRequest("us")
            val items = withContext(Dispatchers.IO) { repository.getHeadlineNews(request) }
            mTopHeadlineLive.value = items
        }
    }
}