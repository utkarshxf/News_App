package com.orion.newsapp.Domain.Repository

import com.orion.newsapp.Domain.Model.Article

interface GetNewsArticleRepo {
    suspend fun getNewsArticle():List<Article>
}