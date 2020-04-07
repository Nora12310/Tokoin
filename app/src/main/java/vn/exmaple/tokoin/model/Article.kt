package vn.exmaple.tokoin.model

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.akd.support.model.IFlexibleItem
import java.text.SimpleDateFormat
import java.util.*


@Entity
data class Article(
    var title: String? = null,
    var author: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: Date? = null,
    var content: String? = null,
    var source: Source? = null,
    var keyword: String? = null
) : IFlexibleItem {
    @PrimaryKey(autoGenerate = true)
    var id: Int = (title + author + url).hashCode()

    override fun areItemsTheSame(iFlexibleItem: IFlexibleItem): Boolean =
        iFlexibleItem is Article && iFlexibleItem.title == title
                && iFlexibleItem.url == url
                && iFlexibleItem.author == author

    override fun areContentsTheSame(iFlexibleItem: IFlexibleItem): Boolean =
        iFlexibleItem is Article
                && iFlexibleItem.author == author
                && iFlexibleItem.title == title
                && iFlexibleItem.description == description
                && iFlexibleItem.url == url
                && iFlexibleItem.urlToImage == urlToImage
                && iFlexibleItem.publishedAt == publishedAt
                && iFlexibleItem.content == content
                && iFlexibleItem.source == source
                && iFlexibleItem.keyword == keyword

    @SuppressLint("SimpleDateFormat")
    fun getDateFormat(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return publishedAt?.let { formatter.format(it) } ?: "n/a"
    }
}