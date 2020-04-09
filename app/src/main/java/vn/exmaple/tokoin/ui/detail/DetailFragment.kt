package vn.exmaple.tokoin.ui.detail

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import org.akd.support.extensions.toast
import vn.exmaple.tokoin.R
import vn.exmaple.tokoin.databinding.FragmentWebViewBinding
import vn.exmaple.tokoin.databinding.PopupWindowViewBinding
import java.net.URL


class DetailFragment : Fragment() {
    private val mBinding: FragmentWebViewBinding by lazy {
        FragmentWebViewBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = mBinding.root

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString("url") ?: "https://google.com"
        mBinding.ivBack.setOnClickListener { activity?.onBackPressed() }
        mBinding.ivMore.setOnClickListener { openPopup(it, url) }
        mBinding.webView.settings.javaScriptEnabled = true
        mBinding.webView.webChromeClient = WebChromeClient()
        mBinding.webView.webViewClient = WebViewClient {
            mBinding.progress.visibility = if (it == PAGE_FINISHED) View.INVISIBLE else View.VISIBLE
        }
        mBinding.webView.loadUrl(url)
        mBinding.tvDomain.text = URL(url).host
        if (URLUtil.isHttpsUrl(url)) {
            val param = mBinding.tvDomain.layoutParams as ConstraintLayout.LayoutParams
            param.marginEnd = resources.getDimensionPixelSize(R.dimen._15sdp)
            mBinding.tvDomain.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_lock_black_24dp,
                0,
                0,
                0
            )
        }
    }

    private fun openPopup(anchorView: View, url: String) {
        val view = PopupWindowViewBinding.inflate(layoutInflater)
        view.tvCopyLink.setOnClickListener {
            val clipboard: ClipboardManager? =
                context?.getSystemService(ClipboardManager::class.java)
            clipboard?.setPrimaryClip(ClipData.newPlainText("Copied Url", url))
            "Url have ben copied".toast(context)
        }
        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it
        val popup = PopupWindow(view.root, width, height, focusable)
        popup.showAsDropDown(anchorView)
    }


    internal class WebViewClient(private val callback: (state: Int) -> Unit) :
        android.webkit.WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            callback(PAGE_FINISHED)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            callback(PAGE_STARTED)
        }
    }

    companion object {
        const val PAGE_STARTED = 1
        const val PAGE_FINISHED = 2
    }
}