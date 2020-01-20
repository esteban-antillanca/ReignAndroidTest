package com.example.reignandroidtest.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Esteban Antillanca on 2020-01-17.
 */
class ArticleRemoteDataSource : ArticleDataSource {
    override fun deleteArticle(article: Article) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getArticles(callback: ArticleDataSource.LoadArticleCallback) {

        val call : Call<List<Article>> = RetrofitInstance().getRetrofit().create(APIService::class.java).getArticles()
        call.enqueue(object : Callback<List<Article>> {
            override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
                response.body()?.let { callback.onArticlesLoaded(it) }
            }

            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                callback.onDataNotAvailable()
            }


        })

    }
}