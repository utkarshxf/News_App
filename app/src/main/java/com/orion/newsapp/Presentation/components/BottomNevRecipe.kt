package com.orion.newsapp.Presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.orion.newsapp.Presentation.screen.AllNews
import com.orion.newsapp.Presentation.screen.HomeScreen
import com.orion.newsapp.Presentation.screen.NewsSearch

//@Composable
//fun MainEntryPoint() {
//    Scaffold(bottomBar = {MainBottomNavigation}) {
//
//    }
//}

@Composable
fun MainNevgation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = BottomNevItem.Highlight.route,
    ){
        composable(BottomNevItem.Highlight.route){
            HomeScreen()
        }
        composable(BottomNevItem.Home.route){
            AllNews()
        }
        composable(BottomNevItem.Search.route){
            NewsSearch()
        }
    }}
    @Composable
    fun MainBottomNavigation(navController : NavController) {
        val items = listOf(
            BottomNevItem.Highlight,
            BottomNevItem.Home,
            BottomNevItem.Search
        )
        BottomNavigation(backgroundColor = Color.Black, contentColor = Color.Gray) {

            val navStack by navController.currentBackStackEntryAsState()
            val currentRoute = navStack?.destination?.route

            items.forEach{item->
                BottomNavigationItem(
                    label = { Text(text = item.lebel)},
                    selected = currentRoute == item.route,
                    onClick = {
                              navController.navigate(item.route){
                                  navController.graph.startDestinationRoute?.let {
                                      popUpTo(item.route)
                                      launchSingleTop = true
                                      restoreState = true
                                  }
                              }
                    },
                    icon = {
                        BadgedBox(badge = {
                            if (currentRoute == item.route){
                                Surface(modifier = Modifier.padding(4.dp),
                                shape = CircleShape,color = Color.Red) {
                                    Text(
                                        text = "yo",
                                        style = MaterialTheme.typography.caption,
                                        modifier = Modifier.padding(4.dp)
                                    )
                                }
                            }
                        }) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null ,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    alwaysShowLabel = true,
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray
                )
            }
        }
    }
