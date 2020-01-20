package com.example.reignandroidtest.data

/**
 * Created by Esteban Antillanca on 2020-01-17.
 */
interface ArticleDataSource {

    interface LoadArticleCallback{

        fun onArticlesLoaded(articles: List<Article>)

        fun onDataNotAvailable()

    }

    fun getArticles(callback: LoadArticleCallback)
    fun deleteArticle(article: Article)
}