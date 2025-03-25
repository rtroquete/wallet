package com.recargapay.wallet.resource.database

import com.recargapay.wallet.resource.database.entity.EntryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EntryJPARepository: JpaRepository<EntryEntity, Long> {

}