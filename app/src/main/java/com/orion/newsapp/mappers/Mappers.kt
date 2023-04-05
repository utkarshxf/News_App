package com.orion.newsapp.mappers

import com.orion.newsapp.Data.Model.ArticleDTO
import com.orion.newsapp.Domain.Model.Article


//toDomain will me called by api impl
fun List<ArticleDTO>.toDomain():List<Article>{
    return map {
        Article(
            content = it.content?:"",
            description = it.description?:"",
            title = it.title?:"",
            urlToImage = it.urlToImage?:""
        )
    }
}