package com.recargapay.wallet.application.response

import com.recargapay.wallet.domain.entity.Entry
import java.math.BigDecimal
import java.time.LocalDateTime

data class StatementResponse(
    val balance: BigDecimal,
    val entries: List<EntryResponse>
)

data class EntryResponse(
    val id: Long = 0,
    val entryValue: BigDecimal,
    val transactionType: String,
    val action: String,
    val createdAt: LocalDateTime,
    val balance: BigDecimal
) {
    companion object {
        fun fromDomain(entry: Entry) =
            EntryResponse(
                id = entry.id,
                entryValue = entry.entryValue,
                transactionType = entry.transactionType.name,
                action = entry.action.name,
                createdAt = entry.createdAt,
                balance = entry.balance
            )
    }
}