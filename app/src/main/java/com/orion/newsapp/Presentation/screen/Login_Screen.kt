package com.orion.newsapp.Presentation.screen

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.firebase.auth.FirebaseAuth
import com.orion.newsapp.Presentation.uiStructure
import com.orion.newsapp.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


private fun Context.buildExoPlayer(uri: Uri)=
    ExoPlayer.Builder(this).build().apply {
        setMediaItem(MediaItem.fromUri(uri))
        repeatMode = Player.REPEAT_MODE_ALL
        playWhenReady = true
        prepare()
    }

private fun Context.buildPlayerView(exoPlayer: ExoPlayer) =
    StyledPlayerView(this).apply{
        player = exoPlayer
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        useController = false
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    }


@Composable
fun login(videoUri: Uri,navHostController: NavHostController){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager: FocusManager = LocalFocusManager.current
    val exoPlayer = remember {
        context.buildExoPlayer(videoUri)
    }
    DisposableEffect(
        AndroidView(
            factory = {it.buildPlayerView(exoPlayer)},
            modifier = Modifier.fillMaxSize()
        )
    ){
        onDispose { exoPlayer.release() }
    }
    ProvideWindowInsets {
        Column(
            Modifier
                .navigationBarsWithImePadding()
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = painterResource(id = R.drawable.icon),null,
                Modifier.size(80.dp),
                tint = Color.White
            )
            TextInput(InputType.Name, keyboardActions = KeyboardActions(onNext = {
                passwordFocusRequester.requestFocus()
            })){
                username = it
            }
            TextInput(InputType.Password, keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                context.doLogin(username,password,navHostController)


            }), focsReqster = passwordFocusRequester){
                password = it
            }
            Button(onClick = { context.doLogin(username,password,navHostController) }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "SIGN IN" , Modifier.padding(vertical = 8.dp))
            }
            Divider(color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp ,
                modifier = Modifier.padding(top = 48.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Don't have an account ?" , color = Color.White)
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Sign UP")
                }
            }
        }
    }

}
sealed class InputType(
    val label:String,
    val icon: ImageVector,
    val keyboardOption: KeyboardOptions,
    val visualTransformation: VisualTransformation
){
    object Name:InputType(
        label = "email",
        icon = Icons.Default.Person,
        keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )
    object Password:InputType(
        label = "Password",
        icon = Icons.Default.Lock,
        keyboardOption = KeyboardOptions(imeAction = ImeAction.Done , keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun TextInput(inputType: InputType,
              focsReqster : FocusRequester? = null,
              keyboardActions: KeyboardActions,
              onClickAction: (String)->Unit) {
    var value by remember { mutableStateOf("") }
    TextField(
        value = value,
        onValueChange = {value = it},
        modifier = Modifier
            .fillMaxWidth()
            .focusOrder(focsReqster ?: FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, null) },
        label = { Text(text = inputType.label) },
        colors = TextFieldDefaults.textFieldColors(
//            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOption,
        visualTransformation = inputType.visualTransformation,
        keyboardActions =keyboardActions,

        )
    onClickAction(value)

}

private fun Context.doLogin(email:String, password:String,navHostController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    when {
        email.isEmpty() -> {
            Toast.makeText(applicationContext, "user name is empty", Toast.LENGTH_SHORT).show()
        }
        password.isEmpty() -> {
            Toast.makeText(applicationContext, "password is empty", Toast.LENGTH_SHORT).show()
        }
        else->{
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            //always run

            }.addOnCanceledListener {
                Toast.makeText(applicationContext, " Try again, Fail to login", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener {
                navHostController.navigate("mainScreen")
            }
        }

    }
}
