package com.example.reignandroidtest.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Esteban Antillanca on 2020-01-20.
 */
class APIResponse {

    @SerializedName("hits")
    var articles = listOf<Article>()
}