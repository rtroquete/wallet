package com.recargapay.wallet.resource.database.entity

import com.recargapay.wallet.domain.entity.Wallet
import com.recargapay.wallet.domain.repository.WalletCreateRequest
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "wallet")
data class WalletEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val number: Long = 0,
    val userId: String,
    val balance: BigDecimal
) {
    fun toDomain() = Wallet(
        number = number,
        userId = userId,
        balance = balance
    )

    companion object {

        fun fromRequest(wallet: WalletCreateRequest) =
            WalletEntity(
                userId = wallet.userId,
                balance = BigDecimal.ZERO
            )

        fun fromDomain(wallet: Wallet) =
            WalletEntity(
                number = wallet.number,
                userId = wallet.userId,
                balance = wallet.balance
            )
    }
}
