package com.orion.newsapp.Presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.orion.newsapp.Domain.Model.Article
import com.orion.newsapp.Presentation.viewmodel.NewsViewmodel

@Composable
    fun HomeScreen(viewModel: NewsViewmodel = hiltViewModel()) {
        val res = viewModel.articles.value

        if (res.isLoading){
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            }
        }
    if (res.error.isNotBlank()){
        Box(modifier = Modifier.fillMaxSize()){
            Text(text = res.error, modifier = Modifier.align(Alignment.Center))
        }
      }

    res.data?.let {
         {

        }
            LazyColumn{
                items (it){
                    ArticalItem(it)
                }
            }
    }
    }

@Composable
fun ArticalItem(it: Article) {

    Column(modifier = Modifier) {
        Image(painter = rememberImagePainter(data = it.urlToImage), contentDescription =null
        , modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit
        )

        Text(text = it.title, style = androidx.compose.ui.text.TextStyle(color = Color.Gray),
            fontWeight = FontWeight.SemiBold ,
        modifier = Modifier.padding(12.dp))
        }
}

