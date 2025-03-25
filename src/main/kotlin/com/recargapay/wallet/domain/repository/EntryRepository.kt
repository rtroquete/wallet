package com.recargapay.wallet.domain.repository

import com.recargapay.wallet.domain.entity.Entry

interface EntryRepository {

    fun createEntry(entry: Entry)
}