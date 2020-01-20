package com.example.reignandroidtest.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Esteban Antillanca on 2020-01-17.
 */
class ArticleDataSource(private val context: Context) : ArticleDataSourceContract {

    private var prefs: SharedPreferences = context.getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        ?: error("err")

    private val gson = Gson()

    override fun deleteArticle(article: Article) {
        saveObjectToArrayList(article, "deleted")
    }

    override fun getDeletedArticles(): List<Article> {
        return fetchArrayList("deleted")
    }

    override fun getCachedArticles(): List<Article> {
        return fetchArrayList("cached")
    }

    override fun checkInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

    override fun getArticles(callback: ArticleDataSourceContract.LoadArticleCallback) {


        if (!checkInternetConnection()) {
            callback.onArticlesLoaded(fetchArrayList("cached"))
            return
        }

        val call : Call<APIResponse> = RetrofitInstance().getRetrofit().create(APIService::class.java).getArticles("api/v1/search_by_date?query=android")
        call.enqueue(object : Callback<APIResponse> {
            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {

                response.body()?.let { response.body()?.articles?.let { it1 ->
                    callback.onArticlesLoaded(
                        it1
                    )
                        clearSharedPref("cached")
                         for( article in it1){

                             saveObjectToArrayList(article,"cached")

                        }
                    }

                }
            }

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                callback.onDataNotAvailable()
            }


        })

    }

    private fun fetchArrayList(key: String): ArrayList<Article> {
        val mArrayList: ArrayList<Article>
        val json = prefs.getString(key, "")

        mArrayList = when {
            json.isNullOrEmpty() -> ArrayList()
            else -> gson.fromJson(json, object : TypeToken<List<Article>>() {}.type)
        }

        return mArrayList
    }

    private fun saveObjectToArrayList(yourObject: Article, key : String) {
        val bookmarks = fetchArrayList(key)
        bookmarks.add(0, yourObject)
        val prefsEditor = prefs.edit()

        val json = gson.toJson(bookmarks)
        prefsEditor.putString(key, json)
        prefsEditor.apply()
    }

    private fun clearSharedPref(key : String){
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.remove(key)
        editor.commit()
    }
}