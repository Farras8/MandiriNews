package com.example.mandiriNews.data.remote

import com.example.mandiriNews.data.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    @GET("top-headlines?country=us&apiKey=0cb3116295304fbba229718fd49a9f6e")
    fun getNews(): Call<NewsResponse>

    @GET("top-headlines?country=us&category=entertainment&apiKey=0cb3116295304fbba229718fd49a9f6e")
    fun getNewsEntertainment(): Call<NewsResponse>

    @GET("top-headlines?country=us&category=business&apiKey=0cb3116295304fbba229718fd49a9f6e")
    fun getNewsBusiness(): Call<NewsResponse>

    @GET("top-headlines?country=us&category=sports&apiKey=0cb3116295304fbba229718fd49a9f6e")
    fun getNewsSports(): Call<NewsResponse>

    @GET("top-headlines?country=us&category=technology&apiKey=0cb3116295304fbba229718fd49a9f6e")
    fun getNewsTechnology(): Call<NewsResponse>

    @GET("top-headlines?country=us&category=health&apiKey=0cb3116295304fbba229718fd49a9f6e")
    fun getNewsHealth(): Call<NewsResponse>

    @GET("top-headlines?country=us&category=science&apiKey=0cb3116295304fbba229718fd49a9f6e")
    fun getNewsScience(): Call<NewsResponse>

    @GET("top-headlines?country=us&category=general&apiKey=0cb3116295304fbba229718fd49a9f6e")
    fun getNewsHeadline(): Call<NewsResponse>
}