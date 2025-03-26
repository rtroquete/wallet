package com.recargapay.wallet

import com.recargapay.wallet.domain.entity.request.EntryCreateRequest
import com.recargapay.wallet.domain.entity.request.TransferCreateRequest
import com.recargapay.wallet.domain.entity.request.WalletCreateRequest
import com.recargapay.wallet.domain.exception.InsufficientBalanceException
import com.recargapay.wallet.domain.exception.WalletNotFoundException
import com.recargapay.wallet.domain.repository.EntryRepository
import com.recargapay.wallet.domain.repository.WalletRepository
import com.recargapay.wallet.domain.service.WalletService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
@Transactional
class WalletApplicationTests {

    @Autowired
	private lateinit var walletService: WalletService

	@Autowired
	private lateinit var walletRepository: WalletRepository

	@Autowired
	private lateinit var entryRepository: EntryRepository

	@Test
	fun `should create a new wallet successfully`() {
		val request = WalletCreateRequest("User123")

		val createdWallet = walletService.create(request)
		val savedWallet = walletRepository.getByNumber(createdWallet.number)

		assertNotNull(savedWallet)
		assertEquals(BigDecimal.ZERO, savedWallet?.balance)
	}

	@Test
	fun `should retrieve the balance of an existing wallet`() {
		val wallet = walletService.create(
			WalletCreateRequest("123")
		)

		assertEquals(BigDecimal.ZERO, walletService.balance(wallet.number))

		val creditRequest = EntryCreateRequest(BigDecimal("66.60"), wallet.number)
		walletService.credit(creditRequest)

		assertEquals(BigDecimal("66.60"), walletService.balance(wallet.number))
	}

	@Test
	fun `should credit an amount to the wallet`() {
		val wallet = walletService.create(
			WalletCreateRequest("123")
		)
		val creditRequest = EntryCreateRequest(BigDecimal("50.00"), wallet.number)
		walletService.credit(creditRequest)

		assertEquals(BigDecimal("50.00"), walletService.balance(wallet.number))
	}

	@Test
	fun `should withdraw an amount from the wallet`() {
		val wallet = walletService.create(
			WalletCreateRequest("123")
		)
		val creditRequest = EntryCreateRequest(BigDecimal("50.00"), wallet.number)
		walletService.credit(creditRequest)

		val withdrawRequest = EntryCreateRequest(BigDecimal("30.00"), wallet.number)
		walletService.withdraw(withdrawRequest)

		assertEquals(BigDecimal("20.00"), walletService.balance(wallet.number))
	}

	@Test
	fun `should transfer an amount between wallets`() {
		val fromWallet = walletService.create(
			WalletCreateRequest("123")
		)
		val toWallet = walletService.create(
			WalletCreateRequest("456")
		)

		val creditRequest = EntryCreateRequest(BigDecimal("50.00"), fromWallet.number)
		walletService.credit(creditRequest)

		val transferRequest = TransferCreateRequest(fromWallet.number, toWallet.number, BigDecimal("40.00"))
		walletService.transfer(transferRequest)

		assertEquals(BigDecimal("10.00"), walletService.balance(fromWallet.number))
		assertEquals(BigDecimal("40.00"), walletService.balance(toWallet.number))
	}

	@Test
	fun `should generate a statement for a wallet`() {
		val wallet = walletService.create(
			WalletCreateRequest("123")
		)
		val startDate = LocalDate.now().minusDays(30)
		val endDate = LocalDate.now()

		walletService.credit(EntryCreateRequest(BigDecimal("50.00"), wallet.number))
		walletService.withdraw(EntryCreateRequest(BigDecimal("50.00"), wallet.number))

		val statement = walletService.statement(wallet.number, startDate, endDate, page = 0)

		assertNotNull(statement)
		assertEquals(2, statement.size)
	}

	@Test
	fun `should throw exception when wallet is not found`() {
		val walletNumber = 123456789L

		assertThrows<WalletNotFoundException> {
			walletService.balance(walletNumber)
		}

	}

	@Test
	fun `should throw insufficient balance exception on withdrawal attempt`() {
		val wallet = walletService.create(
			WalletCreateRequest("123")
		)
		assertThrows<InsufficientBalanceException> {
			walletService.withdraw(EntryCreateRequest(BigDecimal("50.00"), wallet.number))
		}

	}


}
