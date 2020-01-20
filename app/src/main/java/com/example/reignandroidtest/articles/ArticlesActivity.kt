package com.example.reignandroidtest.articles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.reignandroidtest.R
import com.example.reignandroidtest.data.ArticleDataSource
import com.example.reignandroidtest.util.replaceFragmentInActivity

/**
 * Created by Esteban Antillanca on 2020-01-18.
 */
class ArticlesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articles_list_act)

        val articlesFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
            as ArticlesFragment? ?: ArticlesFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }
        var articlesPresenter = ArticlesPresenter(view = articlesFragment , dataSource = ArticleDataSource(applicationContext))
        articlesFragment.presenter = articlesPresenter
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}