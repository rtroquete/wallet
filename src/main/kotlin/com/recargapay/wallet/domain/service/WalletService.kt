package com.recargapay.wallet.domain.service

import com.recargapay.wallet.domain.entity.Entry
import com.recargapay.wallet.domain.entity.Wallet
import com.recargapay.wallet.domain.entity.enums.Action
import com.recargapay.wallet.domain.exception.UserWalletException
import com.recargapay.wallet.domain.repository.EntryRepository
import com.recargapay.wallet.domain.repository.WalletCreateRequest
import com.recargapay.wallet.domain.repository.WalletRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class WalletService(
    private val walletRepository: WalletRepository,
    private val entryRepository: EntryRepository
) {

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

    fun balance(number: Long) {
        logger.info("Retrieving balance for wallet $number")
        walletRepository.getBalance(number)
    }

    fun credit(entry: Entry) {
        logger.info("Processing a credit of ${entry.entryValue} for wallet ${entry.walletNumber}")
        processTransaction(entry)
    }

    fun withdraw(entry: Entry) {
        logger.info("Processing a withdraw of ${entry.entryValue} for wallet ${entry.walletNumber}")
        processTransaction(entry)
    }

    @Transactional
    fun transfer(entryOrigin: Entry, entryDestination: Entry) {

        logger.info(
            "Processing a transfer of ${entryOrigin.entryValue} " +
                "from wallet ${entryOrigin.walletNumber} to wallet ${entryDestination.walletNumber}"
        )

        processTransaction(entryOrigin)
        processTransaction(entryDestination)
    }

    private fun processTransaction(entry: Entry) {
        walletRepository.getByNumber(entry.walletNumber)?.also { wallet ->
            balanceValidation(wallet, entry)

            updateBalance(wallet, entry).also {
                entryRepository.createEntry(entry.copy(balance = it.balance))
            }

            //metric for input or output and value
        }

        throw RuntimeException("Wallet informed not found")
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
            throw RuntimeException("Insufficient balance")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(WalletService::class.java)
    }

}