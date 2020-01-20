package com.example.reignandroidtest.articles

import com.example.reignandroidtest.data.Article
import com.example.reignandroidtest.data.ArticleDataSource

/**
 * Created by Esteban Antillanca on 2020-01-18.
 */
class ArticlesPresenter(private val dataSource: ArticleDataSource, val view: ArticlesContract.View) : ArticlesContract.Presenter {

    override fun loadArticles(showLoadingUI: Boolean) {
        return
        if (showLoadingUI){
            view.setLoadingIndicator(true)
        }

        dataSource.getArticles(object : ArticleDataSource.LoadArticleCallback {
            override fun onArticlesLoaded(articles: List<Article>) {

                val articles = ArrayList<Article>()

                if (showLoadingUI){
                   view.setLoadingIndicator(false)
               }

                if (articles.size == 0){
                    view.showNoArticles()
                }else{
                    //TODO filter deleted articles
                    view.showArticles(articles)
                }

            }

            override fun onDataNotAvailable() {
               view.showLoadingArticlesError()
            }


        } )
    }

    override fun deleteArticle(article: Article) {

        dataSource.deleteArticle(article)
    }

    override fun openArticleDetail(article: Article) {
        view.showArticleWebView(article)
    }

    override fun start() {
        loadArticles(showLoadingUI = true)
    }
}