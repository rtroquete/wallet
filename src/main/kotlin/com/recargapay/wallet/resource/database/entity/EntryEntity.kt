package com.recargapay.wallet.resource.database.entity

import com.recargapay.wallet.domain.entity.Entry
import com.recargapay.wallet.domain.entity.enums.Action
import com.recargapay.wallet.domain.entity.enums.TransactionType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class EntryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val walletNumber: Long,
    val value: BigDecimal,
    val transactionType: TransactionType,
    val action: Action,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromDomain(entry: Entry) =
            EntryEntity(
                walletNumber = entry.walletNumber,
                value = entry.value,
                transactionType = entry.transactionType,
                action = entry.action,
                createdAt = entry.createdAt
            )
    }
}