package com.example.reignandroidtest.articles

import com.example.reignandroidtest.data.Article
import com.example.reignandroidtest.util.BasePresenter
import com.example.reignandroidtest.util.BaseView

/**
 * Created by Esteban Antillanca on 2020-01-17.
 */
interface ArticlesContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showArticles(articles: List<Article>)

        fun showLoadingArticlesError()

        fun showNoArticles()

        fun showArticleRemoved(position: Int)

        fun showArticleWebView(url: String)

        fun showArticleNoDetail()

    }

    interface Presenter : BasePresenter {

        fun loadArticles(showLoadingUI : Boolean)

        fun deleteArticle(article: Article, position: Int)

        fun openArticleDetail(article: Article)

    }
}