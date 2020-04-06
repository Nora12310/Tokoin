package vn.exmaple.tokoin.model

import org.akd.support.model.IFlexibleItem

data class Article(
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null,
    val source: Source? = null
) : IFlexibleItem {
    override fun areItemsTheSame(iFlexibleItem: IFlexibleItem): Boolean =
        iFlexibleItem is Article && iFlexibleItem.title == title

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
}