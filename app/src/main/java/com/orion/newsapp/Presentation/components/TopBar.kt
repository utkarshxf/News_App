package com.orion.newsapp.Presentation.components

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.orion.newsapp.Presentation.sign_in.UserData

@Composable
fun TopAppBar(modifier: Modifier = Modifier,
              userData: UserData?,
              onSignInClick: ()->Unit
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
        AsyncImage(model = userData?.PrifilePictureUrl,
            modifier = Modifier.clickable {
                onSignInClick
                Log.v("clicked","working .  . .  .   .")
            }
                .clip(
                    CircleShape
                )
                .size(50.dp)
                .padding(5.dp),
                contentDescription = null,


        )


    }
}

