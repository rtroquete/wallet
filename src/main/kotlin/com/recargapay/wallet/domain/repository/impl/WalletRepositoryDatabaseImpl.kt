package com.recargapay.wallet.domain.repository.impl

import com.recargapay.wallet.domain.entity.Wallet
import com.recargapay.wallet.domain.repository.WalletCreateRequest
import com.recargapay.wallet.domain.repository.WalletRepository
import com.recargapay.wallet.resource.database.WalletJPARepository
import com.recargapay.wallet.resource.database.entity.WalletEntity
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class WalletRepositoryDatabaseImpl(
    private val repository: WalletJPARepository
): WalletRepository {

    override fun create(wallet: WalletCreateRequest) =
        repository.save(WalletEntity.fromRequest(wallet)).toDomain()

    override fun getByUserId(userId: String) =
        repository.getByUserId(userId)?.toDomain()

    override fun getByNumber(number: Long) =
        repository.getByNumber(number)?.toDomain()

    override fun getBalance(number: Long) =
        this.getByNumber(number)?.balance ?: throw RuntimeException("Wallet informed not found")

    override fun updateBalance(wallet: Wallet) {
        repository.save(WalletEntity.fromDomain(wallet))
    }

}