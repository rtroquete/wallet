package com.recargapay.wallet.domain.service

import com.recargapay.wallet.application.config.AuditLogger
import com.recargapay.wallet.domain.entity.Entry
import com.recargapay.wallet.domain.entity.Wallet
import com.recargapay.wallet.domain.entity.enums.Action
import com.recargapay.wallet.domain.entity.enums.TransactionType
import com.recargapay.wallet.domain.entity.request.EntryCreateRequest
import com.recargapay.wallet.domain.entity.request.TransferCreateRequest
import com.recargapay.wallet.domain.entity.request.WalletCreateRequest
import com.recargapay.wallet.domain.exception.InsufficientBalanceException
import com.recargapay.wallet.domain.exception.UserWalletException
import com.recargapay.wallet.domain.exception.WalletNotFoundException
import com.recargapay.wallet.domain.repository.EntryRepository
import com.recargapay.wallet.domain.repository.WalletRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class WalletService(
    private val walletRepository: WalletRepository,
    private val entryRepository: EntryRepository
): AuditLogger(WalletService::class.java) {

    fun create(walletCreateRequest: WalletCreateRequest): Wallet {

        logger.info("Creating a new wallet for ${walletCreateRequest.userId}")

        walletRepository.getByUserId(walletCreateRequest.userId)?.let {
            logger.warn("User ${walletCreateRequest.userId} already has an existing wallet")
            throw UserWalletException("User just have an wallet")
        }

        val wallet = walletRepository.create(walletCreateRequest)
        logger.info("Wallet ${wallet.number} created")

        return wallet
    }

    fun balance(number: Long): BigDecimal {
        logger.info("Retrieving balance for wallet $number")
        return walletRepository.getBalance(number)
    }

    @Transactional
    fun credit(entryRequest: EntryCreateRequest) {
        val entry = Entry(
            walletNumber = entryRequest.walletNumber,
            entryValue = entryRequest.entryValue,
            transactionType = TransactionType.CREDIT,
            action = Action.INPUT,
            createdAt = LocalDateTime.now()
        )
        logger.info("Processing a credit of ${entry.entryValue} for wallet ${entry.walletNumber}")

        processTransaction(entry)
        audit(entry.action.name, entry.walletNumber, entry.entryValue, entry.transactionType.name)
    }

    @Transactional
    fun withdraw(entryRequest: EntryCreateRequest) {

        val entry = Entry(
            walletNumber = entryRequest.walletNumber,
            entryValue = entryRequest.entryValue,
            transactionType = TransactionType.WITHDRAW,
            action = Action.OUTPUT,
            createdAt = LocalDateTime.now()
        )

        logger.info("Processing a withdraw of ${entry.entryValue} for wallet ${entry.walletNumber}")
        processTransaction(entry)
        audit(entry.action.name, entry.walletNumber, entry.entryValue, entry.transactionType.name)
    }

    @Transactional
    fun transfer(transferCreateRequest: TransferCreateRequest) {

        val entryOrigin = transferCreateRequest.createEntryOrigin()
        val entryDestination = transferCreateRequest.createEntryDestination()

        logger.info(
            "Processing a transfer of ${entryOrigin.entryValue} " +
                "from wallet ${entryOrigin.walletNumber} to wallet ${entryDestination.walletNumber}"
        )

        processTransaction(entryOrigin)
        audit(entryOrigin.action.name, entryOrigin.walletNumber, entryOrigin.entryValue, entryOrigin.transactionType.name)
        processTransaction(entryDestination)
        audit(entryDestination.action.name, entryDestination.walletNumber, entryDestination.entryValue, entryDestination.transactionType.name)
    }

    fun statement(number: Long, start: LocalDate, end: LocalDate, page: Int): List<Entry> {
        logger.info("Retrieving statement for wallet $number")
        return entryRepository.statement(number, start, end, page)
    }

    private fun processTransaction(entry: Entry) {
        walletRepository.getByNumber(entry.walletNumber)?.also { wallet ->
            balanceValidation(wallet, entry)

            updateBalance(wallet, entry).also {
                entryRepository.createEntry(entry.copy(balance = it.balance))
            }

            //metric for input or output and value
        } ?: throw WalletNotFoundException("Wallet informed not found")
    }

    private fun updateBalance(wallet: Wallet, entry: Entry) =
        wallet.copy(
            balance = when (entry.action) {
                Action.INPUT -> wallet.balance + entry.entryValue
                Action.OUTPUT -> wallet.balance - entry.entryValue
            }
        ).also(walletRepository::updateBalance)

    private fun balanceValidation(wallet: Wallet, entry: Entry) {
        if(entry.action == Action.OUTPUT && wallet.balance - entry.entryValue < BigDecimal.ZERO)
            throw InsufficientBalanceException("Insufficient balance")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(WalletService::class.java)
    }

}