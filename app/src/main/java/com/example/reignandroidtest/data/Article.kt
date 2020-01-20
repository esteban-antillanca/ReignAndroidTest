package com.example.reignandroidtest.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Esteban Antillanca on 2020-01-17.
 */
data class Article( @SerializedName("title") var title: String = "",
                    @SerializedName("story_title") var storyTitle: String = "",
                    @SerializedName("author") var author: String = "",
                    @SerializedName("created_at") var created: String = "",
                    @SerializedName("story_id") var id: String = "")