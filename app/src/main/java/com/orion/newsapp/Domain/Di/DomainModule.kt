package com.orion.newsapp.Domain.Di

import com.orion.newsapp.Data.Network.ApiService
import com.orion.newsapp.Data.Repository.GetNewsAritcleRepoImpl
import com.orion.newsapp.Domain.Repository.GetNewsArticleRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    fun provideGetNewsRepo(apiService: ApiService):GetNewsArticleRepo{
        return GetNewsAritcleRepoImpl(apiService = apiService)
    }

}