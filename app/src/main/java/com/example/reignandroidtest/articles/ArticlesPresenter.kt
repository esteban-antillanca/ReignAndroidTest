package com.example.reignandroidtest.articles

import com.example.reignandroidtest.data.Article
import com.example.reignandroidtest.data.ArticleDataSource

/**
 * Created by Esteban Antillanca on 2020-01-18.
 */
class ArticlesPresenter(private val dataSource: ArticleDataSource, val view: ArticlesContract.View) : ArticlesContract.Presenter {

    private var firstLoad = true

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
                    for (article in articles){
                        if (article.title == null || article.title.equals("")) article.title = article.storyTitle
                    }
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

        if (article.URL == null){
            if(article.storyURL == null)
            {
                view.showArticleNoDetail()

            }else{
                view.showArticleWebView(article.storyURL)
            }
            return
        }
        view.showArticleWebView(article.URL)
    }

    override fun start() {
        if (firstLoad) {
            loadArticles(showLoadingUI = true)
            firstLoad = false
        }
    }
}