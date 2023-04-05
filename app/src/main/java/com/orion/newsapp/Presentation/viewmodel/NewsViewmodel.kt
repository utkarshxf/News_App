package com.orion.newsapp.Presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.orion.newsapp.Domain.use_case.GetNewsArticleUseCase
import com.orion.newsapp.Presentation.HomeStateHolder
import com.orion.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn


@HiltViewModel
class NewsViewmodel@Inject constructor(private val getNewsArticleUseCase: GetNewsArticleUseCase):
    ViewModel() {
    val articles = mutableStateOf(HomeStateHolder())
    init {
        getNewsArticles()
    }

    fun getNewsArticles(){
        getNewsArticleUseCase().onEach {

            when(it){
                is Resource.Loading->{
                    articles.value = HomeStateHolder(isLoading = true)
                }
                is Resource.Success->{
                    articles.value = HomeStateHolder(data = it.data)
                }
                is Resource.Error->{
                    articles.value = HomeStateHolder(error = it.message.toString())
                }
            }

        }.launchIn(viewModelScope)
    }
}