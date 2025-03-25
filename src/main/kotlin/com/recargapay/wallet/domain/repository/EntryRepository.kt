package com.recargapay.wallet.domain.repository

import com.recargapay.wallet.domain.entity.Entry

interface EntryRepository {

    fun findAll(): List<Entry>

    fun createEntry(entry: Entry)

    fun statement(walletNumber: Long): List<Entry>
}