package com.orion.newsapp.Presentation.screen



import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.orion.newsapp.Presentation.sign_in.GoogleAuthUiClient
import com.orion.newsapp.Presentation.uiStructure
import com.orion.newsapp.Presentation.viewmodel.SignInViewModel
import kotlinx.coroutines.launch


@Composable
fun LogInController (lifecycleOwner: LifecycleOwner , applicationContext:Context)  {
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    var navController = rememberNavController()
    NavHost(navController = navController, startDestination ="sign_in") {
        composable("sign_in"){
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val launcher = rememberLauncherForActivityResult(
                contract =ActivityResultContracts.StartIntentSenderForResult(),
                onResult = {result ->
                    if (result.resultCode == RESULT_OK){
                        lifecycleOwner.lifecycleScope.launch {
                            val signInResult = googleAuthUiClient.signInwithIntent(
                                intent = result.data?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )
            LaunchedEffect(key1 = state.isSuccessfull){
                if (state.isSuccessfull){
                    Toast.makeText(applicationContext, "successfully login", Toast.LENGTH_SHORT).show()

                }
            }
            SignInScreen(
                state = state,
                onSignInClick = {
                    lifecycleOwner.lifecycleScope.launch {
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

    }
}