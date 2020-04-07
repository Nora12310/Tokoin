package vn.exmaple.tokoin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.akd.support.model.IFlexibleItem

@Entity
data class Account(
    @PrimaryKey var id: String,
    var userName: String,
    var keyword: String
) : IFlexibleItem {
    override fun areItemsTheSame(iFlexibleItem: IFlexibleItem): Boolean =
        iFlexibleItem is Account && iFlexibleItem.id == id

    override fun areContentsTheSame(iFlexibleItem: IFlexibleItem): Boolean =
        iFlexibleItem is Account && iFlexibleItem.id == id
                && iFlexibleItem.userName == userName
                && iFlexibleItem.keyword == keyword
}