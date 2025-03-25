package com.recargapay.wallet.domain.repository.impl

import com.recargapay.wallet.domain.entity.Entry
import com.recargapay.wallet.domain.repository.EntryRepository
import com.recargapay.wallet.resource.database.EntryJPARepository
import com.recargapay.wallet.resource.database.entity.EntryEntity
import org.springframework.stereotype.Repository

@Repository
class EntryRepositoryDatabaseImpl(
    private val repository: EntryJPARepository
): EntryRepository {

    override fun createEntry(entry: Entry) {
        repository.save(EntryEntity.fromDomain(entry))
    }
}