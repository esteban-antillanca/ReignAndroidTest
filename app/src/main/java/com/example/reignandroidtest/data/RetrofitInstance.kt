package com.example.reignandroidtest.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Esteban Antillanca on 2020-01-17.
 */
class RetrofitInstance {

     fun getRetrofit(): Retrofit {

         val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
             this.level = HttpLoggingInterceptor.Level.BODY
         }

         val client : OkHttpClient = OkHttpClient.Builder().apply {
             this.addInterceptor(interceptor)
         }.build()


        return Retrofit.Builder()
            .baseUrl("https://hn.algolia.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}