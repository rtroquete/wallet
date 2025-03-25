package com.recargapay.wallet.domain.entity.request

import com.recargapay.wallet.domain.entity.Entry
import com.recargapay.wallet.domain.entity.enums.Action
import com.recargapay.wallet.domain.entity.enums.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime

class TransferCreateRequest(
    val walletNumberOrigin: Long,
    val walletNumberDestination: Long,
    val value: BigDecimal
) {
    fun createEntryOrigin() =
        Entry(
            walletNumber = walletNumberOrigin,
            entryValue = value,
            transactionType = TransactionType.TRANSFER,
            action = Action.OUTPUT,
            createdAt = LocalDateTime.now()
        )

    fun createEntryDestination() =
        Entry(
            walletNumber = walletNumberDestination,
            entryValue = value,
            transactionType = TransactionType.TRANSFER,
            action = Action.INPUT,
            createdAt = LocalDateTime.now()
        )
}