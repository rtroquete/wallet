package com.recargapay.wallet.application.response

import com.recargapay.wallet.domain.entity.Wallet
import java.math.BigDecimal

data class WalletResponse(
    val number: Long,
    val userId: String,
    val balance: BigDecimal
) {
    companion object {
        fun fromDomain(wallet: Wallet) =
            WalletResponse(
                number = wallet.number,
                userId = wallet.userId,
                balance = wallet.balance
            )
    }
}