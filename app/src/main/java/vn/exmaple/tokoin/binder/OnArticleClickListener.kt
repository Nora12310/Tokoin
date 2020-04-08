package vn.exmaple.tokoin.binder

import vn.exmaple.tokoin.model.Article

interface OnArticleClickListener {
    fun onArticleClicked(article: Article?)
}