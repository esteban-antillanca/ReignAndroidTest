package com.example.reignandroidtest.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Esteban Antillanca on 2020-01-17.
 */
class RetrofitInstance {

     fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://hn.algolia.com/api/v1/search_by_date?query=android")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}