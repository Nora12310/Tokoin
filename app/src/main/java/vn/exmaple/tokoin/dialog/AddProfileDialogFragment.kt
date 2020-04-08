package vn.exmaple.tokoin.dialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import org.akd.support.util.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.exmaple.tokoin.R
import vn.exmaple.tokoin.databinding.DialogAddProfileVieewBinding
import vn.exmaple.tokoin.model.Account

class AddProfileDialogFragment(
    private val delegate: (account: Account) -> Unit
) :
    DialogFragment() {
    private val mBinding: DialogAddProfileVieewBinding by lazy {
        DialogAddProfileVieewBinding.inflate(layoutInflater)
    }

    private val mViewModel: AddProfileDialogViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setGravity(Gravity.START or Gravity.CENTER)
            val params: WindowManager.LayoutParams = attributes
            attributes.windowAnimations = R.style.MyAnimation_Window
            params.x = Utils.dpToPx(context, 20f).toInt()
            // params.y = Utils.dpToPx(context, 45 + 16 + 45f).toInt()
            params.width = resources.displayMetrics.widthPixels - params.x * 2
            attributes = params
            setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.mErrorLive.observe(viewLifecycleOwner, Observer {
            mBinding.tvMessage.text = getString(it)
        })
        mViewModel.mExecuteLive.observe(viewLifecycleOwner, Observer {
            delegate(it)
            dismissAllowingStateLoss()
        })
        mBinding.btnDone.setOnClickListener {
            mViewModel.save(
                mBinding.edtUsername.text.toString(),
                mBinding.edtKeyword.text.toString(),
                mBinding.cbUpdateExistedAccount.isChecked
            )
        }
    }

}