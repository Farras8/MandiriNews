package com.example.mandiriNews.ui.main

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mandiriNews.R
import com.example.mandiriNews.data.remote.ApiClient
import com.example.mandiriNews.data.response.ArticlesItem
import com.example.mandiriNews.data.response.NewsResponse
import com.example.mandiriNews.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NewsAdapter
    private lateinit var btnGeneral: TextView
    private lateinit var btnSport: TextView
    private lateinit var btnHealth: TextView
    private lateinit var btnScience: TextView
    private lateinit var btnTechnology: TextView
    private lateinit var btnBusiness: TextView
    private lateinit var btnEntertainment: TextView
    private lateinit var viewFlipper: ViewFlipper
    private var activeButton: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        initViews()
        initRecyclerView()
        setButtonListeners()

        getNews("general")

        getTopHeadlineNews()

        btnGeneral.setTextColor(ContextCompat.getColor(this, R.color.blue))
        activeButton = btnGeneral
    }

    private fun initViews() {
        btnGeneral = findViewById(R.id.btnGeneral)
        btnSport = findViewById(R.id.btnSport)
        btnHealth = findViewById(R.id.btnHealth)
        btnScience = findViewById(R.id.btnScience)
        btnTechnology = findViewById(R.id.btnTechnology)
        btnBusiness = findViewById(R.id.btnBusiness)
        btnEntertainment = findViewById(R.id.btnEntertainment)
        viewFlipper = findViewById(R.id.viewFlipper)
    }

    private fun initRecyclerView() {
        val rvNews: RecyclerView = findViewById(R.id.recycleViewNews)
        adapter = NewsAdapter {
            openNewsUrl(it)
        }
        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.adapter = adapter
    }

    private fun setButtonListeners() {
        btnGeneral.setOnClickListener { onCategoryClick(it as TextView, "general") }
        btnSport.setOnClickListener { onCategoryClick(it as TextView, "sports") }
        btnHealth.setOnClickListener { onCategoryClick(it as TextView, "health") }
        btnScience.setOnClickListener { onCategoryClick(it as TextView, "science") }
        btnTechnology.setOnClickListener { onCategoryClick(it as TextView, "technology") }
        btnBusiness.setOnClickListener { onCategoryClick(it as TextView, "business") }
        btnEntertainment.setOnClickListener { onCategoryClick(it as TextView, "entertainment") }
    }

    private fun onCategoryClick(selectedButton: TextView, category: String) {
        // Set active button style
        activeButton?.let {
            it.setTextColor(resources.getColor(R.color.gray))
            it.paintFlags = it.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        }

        selectedButton.setTextColor(ContextCompat.getColor(this, R.color.blue))
        activeButton = selectedButton

        getNews(category)
    }

    private fun getNews(category: String) {
        val call: Call<NewsResponse> = when (category) {
            "sports" -> ApiClient.create().getNewsSports()
            "health" -> ApiClient.create().getNewsHealth()
            "science" -> ApiClient.create().getNewsScience()
            "technology" -> ApiClient.create().getNewsTechnology()
            "business" -> ApiClient.create().getNewsBusiness()
            "entertainment" -> ApiClient.create().getNewsEntertainment()
            else -> ApiClient.create().getNews() // Default to general news
        }

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val articles: List<ArticlesItem> = response.body()?.articles as List<ArticlesItem>
                    adapter.setNews(articles)
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun openNewsUrl(newsItem: ArticlesItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_NEWS, newsItem)
        startActivity(intent)
    }

    private fun getTopHeadlineNews() {
        val call: Call<NewsResponse> = ApiClient.create().getNewsHeadline()

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val articles: List<ArticlesItem> = response.body()?.articles as List<ArticlesItem>
                    if (articles.isNotEmpty()) {
                        displayTopHeadlines(articles)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun displayTopHeadlines(headlines: List<ArticlesItem>) {
        viewFlipper.removeAllViews()

        for (newsItem in headlines) {
            val view = LayoutInflater.from(this).inflate(R.layout.headline_item, viewFlipper, false)

            val imageViewHead: ImageView = view.findViewById(R.id.ImageViewHead)
            val titleHead: TextView = view.findViewById(R.id.TitleHead)
            val AuthorHead: TextView = view.findViewById(R.id.AuthorHead)

            titleHead.text = newsItem.title
            AuthorHead.text = newsItem.author
            Glide.with(this)
                .load(newsItem.urlToImage)
                .apply(RequestOptions().dontTransform().placeholder(R.drawable.loading_icon))
                .into(imageViewHead)

            view.setOnClickListener {
                openNewsUrl(newsItem)
            }

            viewFlipper.addView(view)
        }

        viewFlipper.startFlipping()
    }
}
