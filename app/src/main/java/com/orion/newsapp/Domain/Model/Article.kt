package com.orion.newsapp.Domain.Model

import com.orion.newsapp.Data.Model.SourceDTO

data class Article (

    val content: String,
    val description: String,
    val title: String,
    val urlToImage: String
)