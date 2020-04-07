package vn.exmaple.tokoin.ui.filterable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.akd.support.adapter.lists.PageListAdapter
import org.akd.support.extensions.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.exmaple.tokoin.binder.BigNewsViewBinder
import vn.exmaple.tokoin.binder.SmallNewsViewBinder
import vn.exmaple.tokoin.data.local.TopHeadlineBoundaryCallback
import vn.exmaple.tokoin.databinding.FragmentFilterableBinding
import vn.exmaple.tokoin.model.Article

class FilterableFragment : Fragment() {
    private val mViewModel: FilterableViewModel by viewModel()
    private val mAdapter: PageListAdapter by inject()
    private val mBinder: FragmentFilterableBinding by lazy {
        FragmentFilterableBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = mBinder.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        mViewModel.mTopHeadlineLive.observe(viewLifecycleOwner, Observer { mAdapter.submit(it) })
        mViewModel.mStateLive.observe(viewLifecycleOwner, Observer {
            val visibility = if (it == TopHeadlineBoundaryCallback.LOADING) View.VISIBLE else View.GONE
            mBinder.progress.visibility = visibility
        })
        mViewModel.mNewArticleLive.observe(viewLifecycleOwner, Observer {
            if (it > 0) "There are $it new posts".toast(activity)
        })
    }

    private fun initRecyclerView() {
        //register view type for adapter.
        mAdapter.register(Article::class).to(
            BigNewsViewBinder(), SmallNewsViewBinder()
        ).withKotlinClassLinker { index, _ ->
            if (index % 5 == 0) BigNewsViewBinder::class
            else SmallNewsViewBinder::class
        }
        mBinder.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinder.recyclerView.adapter = mAdapter
    }
}