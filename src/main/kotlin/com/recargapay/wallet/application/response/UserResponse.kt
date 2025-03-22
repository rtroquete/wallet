package com.recargapay.wallet.application.response

data class ClientResponse(
    val id: String,
    val name: String,
    val taxIdentifier: String,
    val account: Account
)

data class Account(
    val branch: String,
    val account: String
)