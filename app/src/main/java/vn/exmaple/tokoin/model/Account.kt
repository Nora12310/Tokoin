package vn.exmaple.tokoin.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.akd.support.model.IFlexibleItem

@Entity(tableName = "account")
data class Account(
    @PrimaryKey var id: Int,
    var userName: String,
    var keyword: String
) : IFlexibleItem {
    @Ignore var isActive: Boolean = false
    override fun areItemsTheSame(iFlexibleItem: IFlexibleItem): Boolean =
        iFlexibleItem is Account && iFlexibleItem.id == id

    override fun areContentsTheSame(iFlexibleItem: IFlexibleItem): Boolean =
        iFlexibleItem is Account && iFlexibleItem.id == id
                && iFlexibleItem.userName == userName
                && iFlexibleItem.keyword == keyword
                && iFlexibleItem.isActive == isActive
}