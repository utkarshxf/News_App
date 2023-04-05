package com.orion.newsapp.Presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orion.newsapp.Presentation.screen.HomeScreen
import com.orion.newsapp.Presentation.viewmodel.NewsViewmodel
import com.orion.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme{

                app {
                    Scaffold(topBar = { TopAppBar()}) { contentPadding ->
                        // Screen content
                        Box(modifier = Modifier.padding(contentPadding)) { HomeScreen() }
                    }

                }
            }
        }
    }
}

@Composable
fun app(content : @Composable ()->Unit)
    {
        content()
    }

@Preview(showBackground = true)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.onSurface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "news", color = Color.White,
            style = MaterialTheme.typography.h1
        )

    }
}


