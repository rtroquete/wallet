package com.recargapay.wallet.domain.entity

import com.recargapay.wallet.domain.entity.enums.Action
import com.recargapay.wallet.domain.entity.enums.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime

data class Entry(
    val id: Long,
    val walletNumber: Long,
    val entryValue: BigDecimal,
    val transactionType: TransactionType,
    val action: Action,
    val createdAt: LocalDateTime,
    val balance: BigDecimal = BigDecimal.ZERO
)