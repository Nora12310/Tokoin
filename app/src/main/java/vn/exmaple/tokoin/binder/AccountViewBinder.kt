package vn.exmaple.tokoin.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.binder_profile_view.view.*
import org.akd.support.adapter.lists.base.ItemViewBinder
import org.akd.support.util.preference.SharePrefUtil
import vn.exmaple.tokoin.R
import vn.exmaple.tokoin.common.Constant
import vn.exmaple.tokoin.model.Account

class AccountViewBinder(private val callback: OnAccountClickListener) :
    ItemViewBinder<Account, AccountViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder =
        ViewHolder(inflater.inflate(R.layout.binder_profile_view, parent, false), callback)

    override fun onBindViewHolder(holder: ViewHolder, item: Account) {
        holder.setData(item)
    }

    override fun notifySelectedState(data: Account?, isSelected: Boolean) {
        super.notifySelectedState(data, isSelected)
        data?.isSelected = isSelected
    }

    class ViewHolder(view: View, callback: OnAccountClickListener) : RecyclerView.ViewHolder(view) {
        lateinit var mAccount: Account

        init { view.setOnClickListener { callback.onAccountClicked(this) }}

        fun setData(profile: Account) {
            mAccount = profile
            itemView.tv_full_name.text = profile.userName
            itemView.tv_follow.text = itemView.context.getString(R.string.following)
                .format(profile.keyword)
            itemView.iv_check.visibility = if (profile.isSelected) View.VISIBLE else View.GONE
        }
    }

    interface OnAccountClickListener {
        fun onAccountClicked(holder: ViewHolder)
    }
}