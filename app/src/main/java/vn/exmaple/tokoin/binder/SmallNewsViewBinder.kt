package vn.exmaple.tokoin.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.binder_big_news_view.view.*
import org.akd.support.adapter.lists.base.ItemViewBinder
import vn.exmaple.tokoin.R
import vn.exmaple.tokoin.model.Article

class SmallNewsViewBinder(
    private val callback: OnArticleClickListener
) : ItemViewBinder<Article, SmallNewsViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder =
        ViewHolder(inflater.inflate(R.layout.binder_small_news_view, parent, false), callback)

    override fun onBindViewHolder(holder: ViewHolder, item: Article) {
        holder.setData(item)
    }

    class ViewHolder(view: View, callback: OnArticleClickListener) : RecyclerView.ViewHolder(view) {
        private var mArticle: Article? = null

        init { itemView.setOnClickListener { callback.onArticleClicked(mArticle) } }

        fun setData(article: Article) {
            this.mArticle = article
            itemView.tv_title.text = article.title
            itemView.tv_desc.text = article.description
            itemView.tv_source.text = article.source?.name
            itemView.tv_date.text = article.getDateFormat()
            Picasso.get().load(article.urlToImage)
                .placeholder(R.drawable.error_shot)
                .error(R.drawable.error_shot)
                .into(itemView.iv_thumbnail)
        }
    }
}