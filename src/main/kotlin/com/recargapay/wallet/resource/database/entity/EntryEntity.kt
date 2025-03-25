package com.recargapay.wallet.resource.database.entity

import com.recargapay.wallet.domain.entity.Entry
import com.recargapay.wallet.domain.entity.enums.Action
import com.recargapay.wallet.domain.entity.enums.TransactionType
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "entry")
data class EntryEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val walletNumber: Long,
    @Column(name = "entry_value", precision = 38, scale = 2)
    val entryValue: BigDecimal,
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
    @Enumerated(EnumType.STRING)
    val action: Action,
    val createdAt: LocalDateTime,
    @Column(name = "balance", precision = 38, scale = 2)
    val balance: BigDecimal
) {

    fun toDomain() = Entry(
        id = id,
        walletNumber = walletNumber,
        entryValue = entryValue,
        transactionType = transactionType,
        action = action,
        createdAt = createdAt,
        balance = balance
    )

    companion object {
        fun fromDomain(entry: Entry) =
            EntryEntity(
                walletNumber = entry.walletNumber,
                entryValue = entry.entryValue,
                transactionType = entry.transactionType,
                action = entry.action,
                createdAt = entry.createdAt,
                balance = entry.balance
            )
    }
}