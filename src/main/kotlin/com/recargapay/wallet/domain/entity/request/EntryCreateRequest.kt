package com.recargapay.wallet.domain.entity.request

import java.math.BigDecimal

data class EntryCreateRequest(
    val entryValue: BigDecimal,
    val walletNumber: Long
)