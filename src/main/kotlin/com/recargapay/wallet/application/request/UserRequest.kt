package com.recargapay.wallet.application.request

data class UserRequest(
    val name: String,
    val taxIdentifier: String
)