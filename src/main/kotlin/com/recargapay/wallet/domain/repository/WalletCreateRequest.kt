package com.recargapay.wallet.domain.repository

data class WalletCreateRequest(
    val number: Long,
    val userId: String
)
