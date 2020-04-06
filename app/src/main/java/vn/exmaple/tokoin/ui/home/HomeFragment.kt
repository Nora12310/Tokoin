package vn.exmaple.tokoin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.akd.support.adapter.lists.FlexibleAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.exmaple.tokoin.binder.BigNewsViewBinder
import vn.exmaple.tokoin.binder.SmallNewsViewBinder
import vn.exmaple.tokoin.databinding.FragmentHomeBinding
import vn.exmaple.tokoin.model.Article

class HomeFragment : Fragment() {
    private val mViewModel: HomeViewModel by viewModel()
    private val mAdapter: FlexibleAdapter by inject()
    private val mBinder: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
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
        mViewModel.getTopHeadline()
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
