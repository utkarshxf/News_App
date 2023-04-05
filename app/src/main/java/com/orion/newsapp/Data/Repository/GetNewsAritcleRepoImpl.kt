package com.orion.newsapp.Data.Repository

import com.orion.newsapp.Data.Network.ApiService
import com.orion.newsapp.Domain.Model.Article
import com.orion.newsapp.Domain.Repository.GetNewsArticleRepo
import com.orion.newsapp.mappers.toDomain
import com.orion.newsapp.util.SafeApiRequest
import retrofit2.Response
import javax.inject.Inject

class GetNewsAritcleRepoImpl @Inject constructor(private val apiService: ApiService)
    :GetNewsArticleRepo,SafeApiRequest() {
    override suspend fun getNewsArticle(): List<Article> {
        val Response = safeApiRequest {
            apiService.getNewsArticales()
        }
        return Response?.articles?.toDomain()!!
    }

}