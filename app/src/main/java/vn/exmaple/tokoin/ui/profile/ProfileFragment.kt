package vn.exmaple.tokoin.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.akd.support.adapter.lists.PageListAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.exmaple.tokoin.R
import vn.exmaple.tokoin.binder.AccountViewBinder
import vn.exmaple.tokoin.databinding.FragmentProfileBinding
import vn.exmaple.tokoin.dialog.AddProfileDialogFragment

class ProfileFragment : Fragment(), View.OnClickListener {
    private val mBinding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    private val mViewModel: ProfileViewModel by viewModel()
    private val mAdapter: PageListAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        mBinding.tvAddProfile.setOnClickListener(this)
        mViewModel.mProfilesLive.observe(viewLifecycleOwner, Observer {
            mAdapter.submit(it)
        })
        mViewModel.mAccountIsActiveLive.observe(viewLifecycleOwner, Observer {
            mBinding.tvName.text = it.userName
            mBinding.tvFollow.text = getString(R.string.following).format(it.keyword)
        })
    }

    private fun initRecyclerView() {
        mAdapter.register(AccountViewBinder())
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.recyclerView.adapter = mAdapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_add_profile -> {
                val dialog = AddProfileDialogFragment()
                dialog.show(childFragmentManager, null)
            }
        }
    }
}
