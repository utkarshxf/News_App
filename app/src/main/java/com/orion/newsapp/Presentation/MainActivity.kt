package com.orion.newsapp.Presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orion.newsapp.Presentation.components.MainBottomNavigation
import com.orion.newsapp.Presentation.components.MainNevgation
import com.orion.newsapp.Presentation.screen.HomeScreen
import com.orion.newsapp.Presentation.viewmodel.NewsViewmodel
import com.orion.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.orion.newsapp.Presentation.components.TopAppBar
import com.google.android.gms.auth.api.identity.Identity
import com.orion.newsapp.Presentation.screen.LogInController
import com.orion.newsapp.Presentation.screen.ProfileScreen
import com.orion.newsapp.Presentation.screen.SignInScreen
import com.orion.newsapp.Presentation.sign_in.GoogleAuthUiClient
import com.orion.newsapp.Presentation.sign_in.UserData
import com.orion.newsapp.Presentation.viewmodel.SignInViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {


                    var navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            LaunchedEffect(key1 = Unit){
                                if (googleAuthUiClient.getSignInUser() != null){
                                    navController.navigate("main")
                                }
                            }
                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInwithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )
                            LaunchedEffect(key1 = state.isSuccessfull) {
                                if (state.isSuccessfull) {
                                    Toast.makeText(
                                        applicationContext,
                                        "successfully login",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.navigate("profile")
                                    viewModel.resetState()
                                }
                            }
                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }
                        composable("main"){
                            uiStructure(userData = googleAuthUiClient.getSignInUser(),
                            onSignInClick = {
//                                lifecycleScope.launch{
//                                navController.navigate("profile")
//                                   }
                               }
                            )
                        }
                        composable("profile"){
                            ProfileScreen(userData = googleAuthUiClient.getSignInUser(), onSignOut = {lifecycleScope.launch{
                                googleAuthUiClient.signOut()
                                Toast.makeText(
                                    applicationContext,
                                    "signed_out",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.popBackStack()
                                   }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun uiStructure(userData: UserData? ,onSignInClick: ()->Unit) {
    app {
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                TopAppBar(userData = userData , onSignInClick = onSignInClick)
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


@Composable
fun app(content : @Composable ()->Unit)
    {
        content()
    }


