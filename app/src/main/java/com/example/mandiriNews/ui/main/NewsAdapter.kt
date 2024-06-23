package com.example.mandiriNews.ui.main

import android.view.LayoutInflater
import com.example.mandiriNews.R
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mandiriNews.data.response.ArticlesItem

class NewsAdapter(private val listener: (ArticlesItem) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var news = listOf<ArticlesItem>()

    fun setNews(news: List<ArticlesItem>) {
        this.news = news
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycle1, parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount(): Int {
        return news.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val ImageNews: ImageView = itemView.findViewById((R.id.imageNews))
        val tvSource: TextView = itemView.findViewById(R.id.tvSource)

        fun bind(news: ArticlesItem) {
            tvTitle.text = news.title
            tvSource.text = news.author

            Glide.with(itemView.context).load(news.urlToImage)
                .apply(RequestOptions().dontTransform().placeholder(R.drawable.loading_icon))
                .into(ImageNews)

            itemView.setOnClickListener {
                listener(news)
            }
        }
    }
}