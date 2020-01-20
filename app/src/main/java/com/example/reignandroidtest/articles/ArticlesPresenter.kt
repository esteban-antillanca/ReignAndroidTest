package com.example.reignandroidtest.articles

import com.example.reignandroidtest.data.Article
import com.example.reignandroidtest.data.ArticleDataSource

/**
 * Created by Esteban Antillanca on 2020-01-18.
 */
class ArticlesPresenter(private val dataSource: ArticleDataSource, val view: ArticlesContract.View) : ArticlesContract.Presenter {

    override fun loadArticles(showLoadingUI: Boolean) {

        if (showLoadingUI){
            view.setLoadingIndicator(true)
        }

        dataSource.getArticles(object : ArticleDataSource.LoadArticleCallback {
            override fun onArticlesLoaded(articles: List<Article>) {



                if (showLoadingUI){
                   view.setLoadingIndicator(false)
               }

                if (articles.isEmpty()){
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

    override fun deleteArticle(article: Article, position : Int) {

        dataSource.deleteArticle(article)
        view.showArticleRemoved(position)
    }

    override fun openArticleDetail(article: Article) {
        view.showArticleWebView(article)
    }

    override fun start() {
        loadArticles(showLoadingUI = true)
    }
}