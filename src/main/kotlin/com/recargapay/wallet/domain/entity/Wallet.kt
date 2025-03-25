package com.recargapay.wallet.domain.entity

import java.math.BigDecimal

data class Wallet(
    val number: Long,
    val userId: String,
    val balance: BigDecimal,
    val entries: List<Entry> = listOf()
)