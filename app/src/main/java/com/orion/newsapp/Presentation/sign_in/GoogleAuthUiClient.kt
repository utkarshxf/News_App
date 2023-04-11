package com.orion.newsapp.Presentation.sign_in

import android.app.PendingIntent.CanceledException
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.orion.newsapp.R
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient:SignInClient
    ) {
            private val auth = Firebase.auth

            suspend fun signIn(): IntentSender?{
                val result = try {
                    oneTapClient.beginSignIn(
                        buildSignInRequest()
                    ).await()
                }catch (e:Exception){
                    e.printStackTrace()
                    if (e is CanceledException) throw e
                    null
                }
                return result?.pendingIntent?.intentSender
            }
    suspend fun signInwithIntent(intent: Intent):SignInResult{
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdTocken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdTocken, null)
        return try {
            var user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run{
                                UserData(
                                    userId = uid,
                                    username = displayName,
                                    PrifilePictureUrl = photoUrl?.toString(),
                                    PhoneNo = phoneNumber
                                )
                },
                errorMessage = null
            )
        }catch (e:Exception){
            e.printStackTrace()
            if (e is CanceledException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    suspend fun signOut(){
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }catch (e:Exception){
            e.printStackTrace()
            if (e is CanceledException) throw e

        }
    }
    fun getSignInUser():UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName.toString(),
            PrifilePictureUrl = photoUrl.toString(),
            PhoneNo = phoneNumber
        )
    }

    private fun buildSignInRequest():BeginSignInRequest{
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )

            .setAutoSelectEnabled(true)
            .build()

    }
}