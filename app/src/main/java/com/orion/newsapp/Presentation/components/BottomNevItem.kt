package com.orion.newsapp.Presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed  class BottomNevItem(val icon:ImageVector , val lebel : String , val route : String) {

    object Home : BottomNevItem(icon = Icons.Default.Home, lebel = "Home" , route = "home" )
    object Search : BottomNevItem(icon = Icons.Default.Search, lebel = "Search" , route = "Search" )
    object Highlight : BottomNevItem(icon = Icons.Default.List, lebel = "Highlight" , route = "Highlight" )

}