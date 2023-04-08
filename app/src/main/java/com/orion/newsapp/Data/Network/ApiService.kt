package com.orion.newsapp.Data.Network

import com.orion.newsapp.Data.Model.NewsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
//    https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=255cbadf57e842d1867b0053955d2bca
//    after ? everything represent query
    @GET("top-headlines")
    suspend fun getNewsArticales(
    //we have to pass the query
    @Query("country") country:String = "in",
    @Query("apiKey") apiKey:String = "255cbadf57e842d1867b0053955d2bca"
    ):Response<NewsDTO>
}