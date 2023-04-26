package com.orion.newsapp.Presentation

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.orion.newsapp.Presentation.components.MainBottomNavigation
import com.orion.newsapp.Presentation.components.MainNevgation
import com.orion.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.orion.newsapp.Presentation.components.TopAppBar
import com.orion.newsapp.Presentation.screen.login
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val auth = FirebaseAuth.getInstance()
                    if (auth.currentUser==null){
                        NavHost(navController = navController, startDestination = "login") {
                            composable("login") { login(navController) }
                            composable("mainScreen") { uiStructure() }
                        }
                    }else{
                        NavHost(navController = navController, startDestination = "mainScreen") {
                            composable("login") { login(navController) }
                            composable("mainScreen") { uiStructure() }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Context.login(navHostController: NavHostController) {
     fun getVideoUri(): Uri {
        val rawId:Int = resources.getIdentifier("animated","raw",packageName)
        val videoUri = "android.resource://$packageName/$rawId"
        return Uri.parse(videoUri)
    }
    login(getVideoUri(), navHostController)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun uiStructure() {
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
            MainNevgation(navHostController = navController)
        }

    }
}


@Composable
fun app(content : @Composable ()->Unit)
    {
        content()
    }



