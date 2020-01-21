package com.example.reignandroidtest.articles

import android.util.Patterns
import com.example.reignandroidtest.data.Article
import com.example.reignandroidtest.data.ArticleDataSourceContract
import com.example.reignandroidtest.util.UnixToHuman
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


/**
 * Created by Esteban Antillanca on 2020-01-18.
 */
class ArticlesPresenter(private val dataSource: ArticleDataSourceContract, val view: ArticlesContract.View) : ArticlesContract.Presenter {

    private var firstLoad = true
    private var sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").apply{timeZone = TimeZone.getTimeZone("GMT")}

    override fun loadArticles(showLoadingUI: Boolean) {

        if (showLoadingUI){
            view.setLoadingIndicator(true)
        }

        dataSource.getArticles(object : ArticleDataSourceContract.LoadArticleCallback {
            override fun onArticlesLoaded(articles: List<Article>) {



                if (showLoadingUI){
                   view.setLoadingIndicator(false)
               }



                if (articles.isEmpty()){
                    view.showNoArticles()
                }else{
                    // filter deleted articles
                    val deleted = dataSource.getDeletedArticles()

                    var filteredArticles = customFilter(articles,deleted)

                    for (article in filteredArticles){
                        if (article.title == null || article.title.equals("")) article.title = article.storyTitle

                        article.prettyDate = UnixToHuman.getTimeAgo(sdf.parse(article.created).time)

                    }
                    view.showArticles(filteredArticles)
                }

            }

            override fun onDataNotAvailable() {
               view.showLoadingArticlesError()
            }


        } )
    }
    
    fun customFilter(first: List<Article> , second: List<Article>) : List<Article>{
        var newList = ArrayList<Article>()
        var shouldAdd: Boolean
        for (article in first){
            shouldAdd = true
            for (deleted in second){
                if (article.id == deleted.id){
                    shouldAdd = false
                    continue
                }
            }
            if (shouldAdd) newList.add(article)
        }
        
        
        return newList
    }

    override fun deleteArticle(article: Article, position : Int) {

        dataSource.deleteArticle(article)
        view.showArticleRemoved(position)
    }

    override fun openArticleDetail(article: Article) {

        if (!dataSource.checkInternetConnection()){
            view.showArticleNotAvailableOffline()
            return
        }

        if (article.URL == null){
            if(article.storyURL == null)
            {
                view.showArticleNoDetail()

            }else{

                extractUrls(article.storyURL)?.get(0)?.let { view.showArticleWebView(it) }
            }
            return
        }
        extractUrls(article.URL)?.get(0)?.let { view.showArticleWebView(it) }
    }

    override fun start() {
        if (firstLoad) {
            loadArticles(showLoadingUI = true)
            firstLoad = false
        }
    }

    fun extractUrls(input: String): List<String>? {
        val result: MutableList<String> = ArrayList()
        val words = input.split("\\s+").toTypedArray()
        val pattern: Pattern = Patterns.WEB_URL
        for (word in words) {
            if (pattern.matcher(word).find()) {
                if (!word.toLowerCase().contains("http://") && !word.toLowerCase().contains("https://")) {
                    val word = "http://$word"
                }
                result.add(word)
            }
        }
        return result
    }
}