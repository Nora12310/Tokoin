package vn.exmaple.tokoin.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.binder_profile_view.view.*
import org.akd.support.adapter.lists.base.ItemViewBinder
import vn.exmaple.tokoin.R
import vn.exmaple.tokoin.model.Account

class AccountViewBinder : ItemViewBinder<Account, AccountViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder =
        ViewHolder(inflater.inflate(R.layout.binder_profile_view, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, item: Account) {
        holder.setData(item)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setData(profile: Account) {
            itemView.tv_full_name.text = profile.userName
            itemView.tv_follow.text = itemView.context.getString(R.string.following)
                .format(profile.keyword)

            itemView.iv_check.visibility = if (profile.isActive) View.VISIBLE else View.GONE
        }
    }
}