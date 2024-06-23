package com.example.mandiriNews.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.mandiriNews.R
import com.example.mandiriNews.data.response.ArticlesItem

class DetailActivity : AppCompatActivity() {
    companion object{
        val EXTRA_NEWS = "extraNews"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val news = intent.getParcelableExtra<ArticlesItem>(EXTRA_NEWS)


        news?.url?.let { url ->
            val webView: WebView = findViewById(R.id.webView)
            webView.visibility = android.view.View.VISIBLE
            webView.loadUrl(url)
            webView.webViewClient = WebViewClient()
        }
    }
}