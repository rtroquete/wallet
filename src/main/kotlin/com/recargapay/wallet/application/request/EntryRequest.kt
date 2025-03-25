package com.recargapay.wallet.application.request

import java.math.BigDecimal

data class EntryRequest(
    val entryValue: BigDecimal
)