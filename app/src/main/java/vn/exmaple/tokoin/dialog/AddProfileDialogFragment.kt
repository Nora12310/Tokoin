package vn.exmaple.tokoin.dialog

import android.view.Gravity
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import org.akd.support.util.Utils
import vn.exmaple.tokoin.R

class AddProfileDialogFragment : DialogFragment(R.layout.dialog_add_profile_vieew) {

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


}