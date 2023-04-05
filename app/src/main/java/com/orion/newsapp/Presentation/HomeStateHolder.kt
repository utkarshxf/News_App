package com.orion.newsapp.Presentation

import com.orion.newsapp.Domain.Model.Article

data class HomeStateHolder(
    val isLoading:Boolean=false,
    val data:List<Article>?=null,
    val error: String=""
)
