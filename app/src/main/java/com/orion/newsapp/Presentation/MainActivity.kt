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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.orion.newsapp.Presentation.components.MainBottomNavigation
import com.orion.newsapp.Presentation.components.MainNevgation
import com.orion.newsapp.Presentation.screen.HomeScreen
import com.orion.newsapp.Presentation.viewmodel.NewsViewmodel
import com.orion.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.orion.newsapp.Presentation.components.TopAppBar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme{
                app {
                    val navController = rememberNavController()
                    Scaffold(
                        topBar = {
                            TopAppBar()
                        },
                        bottomBar = {
                            MainBottomNavigation(navController = navController)
                        },
                        modifier = Modifier) {
//                        Box(modifier = Modifier.padding(contentPadding)) { HomeScreen() }
                        MainNevgation(navHostController = navController)
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



