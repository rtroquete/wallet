package com.recargapay.wallet.application.response

data class UserResponse(
    val id: String,
    val name: String,
    val taxIdentifier: String,
    val number: Long
)

