package com.example.reignandroidtest.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Esteban Antillanca on 2020-01-17.
 */
interface APIService {
    @GET
    fun getArticles(): Call<List<Article>>
}