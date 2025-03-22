package com.recargapay.wallet.application.request

data class ClientRequest(
    val name: String,
    val taxIdentifier: String
)