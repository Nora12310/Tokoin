package vn.exmaple.tokoin.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.akd.support.adapter.lists.PageListAdapter
import org.akd.support.util.preference.SharePrefUtil
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.exmaple.tokoin.R
import vn.exmaple.tokoin.binder.AccountViewBinder
import vn.exmaple.tokoin.common.Constant
import vn.exmaple.tokoin.databinding.FragmentProfileBinding
import vn.exmaple.tokoin.dialog.AddProfileDialogFragment
import vn.exmaple.tokoin.model.Account
import org.akd.support.adapter.lists.base.SingleChoiceMode

class ProfileFragment : Fragment(), View.OnClickListener, AccountViewBinder.OnAccountClickListener {
    private val mBinding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    private val mSharePref: SharePrefUtil by lazy { SharePrefUtil.with(context).ok() }
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
            selectedProfileIfNeeded()
        })
        mViewModel.mAccountIsActiveLive.observe(viewLifecycleOwner, Observer { updateUi(it) })
        mViewModel.mQueriesAccount.observe(viewLifecycleOwner, Observer {
            val index = mAdapter.indexOf(it)
            mBinding.recyclerView.post { mAdapter.setSelected(index, true) }
        })
    }

    private fun initRecyclerView() {
        mAdapter.register(AccountViewBinder(this))
        mAdapter.setChoiceMode(SingleChoiceMode())
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.recyclerView.adapter = mAdapter
    }

    private fun selectedProfileIfNeeded() {
        val id = mSharePref.readIntPref(Constant.ACCOUNT_ID, 0)
        mViewModel.getQueriesAccount(id)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_add_profile -> {
                val dialog = AddProfileDialogFragment {
                    updateUi(it)
                }
                dialog.show(childFragmentManager, null)
            }
        }
    }

    private fun updateUi(account: Account) {
        mBinding.tvName.text = account.userName
        mBinding.tvFollow.text = getString(R.string.following).format(account.keyword)
    }

    override fun onAccountClicked(holder: AccountViewBinder.ViewHolder) {
        mAdapter.setSelected(holder.adapterPosition, true)
        updateUi(holder.mAccount)
        mSharePref.saveIntPref(Constant.ACCOUNT_ID, holder.mAccount.id)
    }
}
