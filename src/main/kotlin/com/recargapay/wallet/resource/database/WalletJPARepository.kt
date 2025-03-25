package com.recargapay.wallet.resource.database

import com.recargapay.wallet.resource.database.entity.WalletEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletJPARepository: JpaRepository<WalletEntity, Long> {

    fun getByUserId(userId: String): WalletEntity?
    fun getByNumber(number: Long): WalletEntity?

}