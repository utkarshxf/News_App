package com.orion.newsapp.Presentation.sign_in

data class SignInResult (
    val data: UserData?,
    val errorMessage: String?

    )
data class UserData(
    val userId:String?,
    val username : String?,
    val PrifilePictureUrl : String?,
    val PhoneNo: String?
)