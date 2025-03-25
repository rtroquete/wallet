package com.recargapay.wallet.resource.database

import com.recargapay.wallet.resource.database.entity.EntryEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface EntryJPARepository: JpaRepository<EntryEntity, Long> {

    fun findEntryByWalletNumberAndCreatedAtBetween(
        walletNumber: Long, start: LocalDateTime?, end: LocalDateTime?, pageable: Pageable
    ): Page<EntryEntity>

}