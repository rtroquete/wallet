package com.recargapay.wallet.domain.repository.impl

import com.recargapay.wallet.domain.entity.Entry
import com.recargapay.wallet.domain.repository.EntryRepository
import com.recargapay.wallet.resource.database.EntryJPARepository
import com.recargapay.wallet.resource.database.entity.EntryEntity
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Repository
class EntryRepositoryDatabaseImpl(
    private val repository: EntryJPARepository
): EntryRepository {

    override fun findAll(): List<Entry> {
        return repository.findAll().map {
            it.toDomain()
        }
    }

    override fun createEntry(entry: Entry) {
        repository.save(EntryEntity.fromDomain(entry))
    }

    override fun statement(walletNumber: Long): List<Entry> {
        return repository.findEntryByWalletNumberAndCreatedAtBetween(
            walletNumber, LocalDate.now().atStartOfDay(), LocalDateTime.of(LocalDate.now(), LocalTime.MAX), PageRequest.of(0, 10)
        ).toList().map { it.toDomain() }
    }
}