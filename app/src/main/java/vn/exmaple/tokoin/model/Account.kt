package vn.exmaple.tokoin.model

import org.akd.support.model.IFlexibleItem

data class Account(
    val id: String,
    val userName: String,
    val keyword: String
) : IFlexibleItem {
    override fun areItemsTheSame(iFlexibleItem: IFlexibleItem): Boolean =
        iFlexibleItem is Account && iFlexibleItem.id == id

    override fun areContentsTheSame(iFlexibleItem: IFlexibleItem): Boolean =
        iFlexibleItem is Account && iFlexibleItem.id == id
                && iFlexibleItem.userName == userName
                && iFlexibleItem.keyword == keyword
}