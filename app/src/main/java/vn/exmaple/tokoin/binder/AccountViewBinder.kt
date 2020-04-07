package vn.exmaple.tokoin.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        fun setData(profile: Account) {}
    }
}