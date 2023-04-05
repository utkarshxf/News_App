package com.orion.newsapp.Presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.orion.newsapp.Domain.use_case.GetNewsArticleUseCase
import com.orion.newsapp.Presentation.HomeStateHolder
import com.orion.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@HiltViewModel
class NewsViewmodel@Inject constructor(private val getNewsArticleUseCase: GetNewsArticleUseCase):
    ViewModel() {
    val articles = mutableListOf(HomeStateHolder())


    fun getNewsArticles(){
        getNewsArticleUseCase().onEach {

            when(it){
                is Resource.Loading->{

                }
                is Resource.Success->{

                }
                is Resource.Error->{}
            }

        }
    }
}