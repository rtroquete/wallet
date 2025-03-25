package com.recargapay.wallet.application.request

import java.math.BigDecimal

data class TransferRequest(
    val walletNumber: Long,
    val value: BigDecimal
)