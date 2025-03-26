package com.recargapay.wallet.domain.service

import com.recargapay.wallet.domain.entity.Wallet
import com.recargapay.wallet.domain.entity.request.EntryCreateRequest
import com.recargapay.wallet.domain.entity.request.WalletCreateRequest
import com.recargapay.wallet.domain.repository.EntryRepository
import com.recargapay.wallet.domain.repository.WalletRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class WalletServiceUnitTest {

    private val walletRepository = mockk<WalletRepository>()
    private val entryRepository = mockk<EntryRepository>()
    private val walletService = WalletService(walletRepository, entryRepository)

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun `should create a new wallet successfully`() {
        val request = WalletCreateRequest(userId = "123")
        val wallet = Wallet(number = 1, userId = "123", balance = BigDecimal.ZERO)

        every { walletRepository.create(any()) } returns wallet
        every { walletRepository.getByUserId(any()) } returns null
        val createdWallet = walletService.create(request)

        assertNotNull(createdWallet)
        assertEquals(wallet.number, createdWallet.number)
        assertEquals(wallet.userId, createdWallet.userId)
        verify(exactly = 1) { walletRepository.create(any()) }
    }

    @Test
    fun `should retrieve the balance of an existing wallet`() {
        val wallet = Wallet(number = 1, userId = "123", balance = BigDecimal("100.00"))
        every { walletRepository.getByNumber(wallet.number) } returns wallet

        every { walletRepository.getBalance(wallet.number) } returns wallet.balance
        val balance = walletService.balance(wallet.number)

        assertEquals(BigDecimal("100.00"), balance)
    }

    @Test
    fun `should credit an amount to the wallet`() {
        val wallet = Wallet(number = 1, userId = "123", balance = BigDecimal("100.00"))
        val request = EntryCreateRequest(BigDecimal("50.00"), wallet.number)

        every { walletRepository.getByNumber(wallet.number) } returns wallet
        every { entryRepository.createEntry(any()) } just runs
        every { walletRepository.updateBalance(any()) } just runs

        walletService.credit(request)

        verify(exactly = 1) { walletRepository.getByNumber(wallet.number) }
        verify(exactly = 1) { walletRepository.updateBalance(any()) }
    }

}