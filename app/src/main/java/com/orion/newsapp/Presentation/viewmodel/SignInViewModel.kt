package com.orion.newsapp.Presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.orion.newsapp.Presentation.sign_in.SignInResult
import com.orion.newsapp.Presentation.sign_in.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel:ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult){
        _state.update { it.copy(
            isSuccessfull = result.data != null,
            SignInError = result.errorMessage

        ) }
    }
    fun resetState(){
        _state.update { SignInState() }
    }
}