package com.orion.newsapp.Presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun TopAppBar(modifier: Modifier = Modifier
    ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Black),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            fontSize = 25.sp,
            text = "news", color = Color.White,
            style = MaterialTheme.typography.h1
        )
    }
}

