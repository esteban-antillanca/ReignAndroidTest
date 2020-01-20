package com.example.reignandroidtest.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Esteban Antillanca on 2020-01-17.
 */
class ArticleRemoteDataSource : ArticleDataSource {
    override fun deleteArticle(article: Article) {
        //TODO esto falta
    }

    override fun getArticles(callback: ArticleDataSource.LoadArticleCallback) {


        val call : Call<APIResponse> = RetrofitInstance().getRetrofit().create(APIService::class.java).getArticles("api/v1/search_by_date?query=android")
        call.enqueue(object : Callback<APIResponse> {
            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {

                response.body()?.let { response.body()?.articles?.let { it1 ->
                    callback.onArticlesLoaded(
                        it1
                    )
                } }
            }

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                callback.onDataNotAvailable()
            }


        })

    }
}