package com.example.reignandroidtest.articleDetail

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.reignandroidtest.R

/**
 * Created by Esteban Antillanca on 2020-01-20.
 */
class ArticleDetailActivity : AppCompatActivity() {

    private  lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_webview)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = findViewById(R.id.wv_pb)


        val webView = findViewById<WebView>(R.id.article_webview)
        webView.loadUrl(intent.getStringExtra(EXTRA_ARTICLE_URL))

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_ARTICLE_URL = "ARTICLE_URL"
    }
}