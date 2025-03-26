package com.recargapay.wallet.domain.repository

import com.recargapay.wallet.domain.entity.Entry
import java.time.LocalDate

interface EntryRepository {

    fun findAll(): List<Entry>

    fun createEntry(entry: Entry)

    fun statement(walletNumber: Long, start: LocalDate, end: LocalDate, page: Int): List<Entry>
}