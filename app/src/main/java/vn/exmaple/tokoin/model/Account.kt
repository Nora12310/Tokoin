package vn.exmaple.tokoin.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.akd.support.model.IFlexibleItem
import java.util.*

@Entity(tableName = "account")
data class Account(
    @PrimaryKey var id: Int,
    var userName: String,
    var keyword: String,
    var timestamp: Date = Calendar.getInstance().time
) : IFlexibleItem {
    @Ignore
    var isSelected: Boolean = false

    override fun areItemsTheSame(iFlexibleItem: IFlexibleItem): Boolean =
        iFlexibleItem is Account && iFlexibleItem.id == id

    override fun areContentsTheSame(iFlexibleItem: IFlexibleItem): Boolean =
        iFlexibleItem is Account && iFlexibleItem.id == id
                && iFlexibleItem.userName == userName
                && iFlexibleItem.keyword == keyword
                && iFlexibleItem.isSelected == isSelected

    override fun equals(other: Any?): Boolean {
        return other is IFlexibleItem && areItemsTheSame(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}