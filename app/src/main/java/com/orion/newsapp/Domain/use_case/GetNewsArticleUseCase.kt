package com.orion.newsapp.Domain.use_case

import com.orion.newsapp.Domain.Model.Article
import com.orion.newsapp.Domain.Repository.GetNewsArticleRepo
import com.orion.newsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetNewsArticleUseCase @Inject constructor(private val getNewsArticleRepo: GetNewsArticleRepo) {


    operator fun invoke(): Flow<Resource<List<Article>>> = flow {

        try {
            emit(Resource.Loading(""))
            emit(Resource.Success(getNewsArticleRepo.getNewsArticle()))
        }catch (e:java.lang.Exception){
            emit(Resource.Error(e.message))
        }
    }
}