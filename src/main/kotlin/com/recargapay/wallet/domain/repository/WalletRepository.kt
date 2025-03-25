package com.recargapay.wallet.domain.repository

import com.recargapay.wallet.domain.entity.Wallet
import com.recargapay.wallet.domain.entity.request.WalletCreateRequest
import java.math.BigDecimal
import java.time.LocalDate

interface WalletRepository {

    fun create(wallet: WalletCreateRequest): Wallet

    fun getByUserId(userId: String): Wallet?

    fun getByNumber(number: Long): Wallet?

    fun getBalance(number: Long): BigDecimal

    fun updateBalance(wallet: Wallet)

}