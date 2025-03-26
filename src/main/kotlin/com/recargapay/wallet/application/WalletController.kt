package com.recargapay.wallet.application

import com.recargapay.wallet.application.config.AuditLogger
import com.recargapay.wallet.application.request.EntryRequest
import com.recargapay.wallet.application.request.TransferRequest
import com.recargapay.wallet.application.request.WalletRequest
import com.recargapay.wallet.application.response.BalanceResponse
import com.recargapay.wallet.application.response.EntryResponse
import com.recargapay.wallet.application.response.StatementResponse
import com.recargapay.wallet.application.response.WalletResponse
import com.recargapay.wallet.domain.entity.request.EntryCreateRequest
import com.recargapay.wallet.domain.entity.request.TransferCreateRequest
import com.recargapay.wallet.domain.entity.request.WalletCreateRequest
import com.recargapay.wallet.domain.service.WalletService
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("wallet")
class WalletController(
    private val walletService: WalletService
) {

    @PostMapping
    fun create(@RequestBody request: WalletRequest): ResponseEntity<WalletResponse> {
        logger.info("Received request to create wallet for ${request.userId}")
        return walletService.create(WalletCreateRequest(request.userId)).let {
            ResponseEntity.ok(WalletResponse.fromDomain(it))
        }
    }

    @GetMapping("number/{number}/balance")
    fun balance(@PathVariable number: Long): ResponseEntity<BalanceResponse> {
        logger.info("Received request to get balance for wallet $number")
        return ResponseEntity.ok(BalanceResponse(walletService.balance(number)))
    }

    @GetMapping("number/{number}/statement")
    fun statement(
        @PathVariable number: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) start: LocalDate = LocalDate.now(),
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) end: LocalDate = LocalDate.now()
    ): ResponseEntity<StatementResponse> {

        val balance = walletService.balance(number)
        val entries = walletService.statement(number, start, end, page)
        return ResponseEntity.ok(
            StatementResponse(
                balance = balance,
                entries = entries.map { EntryResponse.fromDomain(it) }
            )
        )
    }

    @PostMapping("number/{number}/credit")
    fun credit(
        @PathVariable number: Long,
        @RequestBody entryRequest: EntryRequest
    ) {
        logger.info("Received request to credit balance for wallet $number")

        walletService.credit(
            EntryCreateRequest(
                entryValue = entryRequest.entryValue,
                walletNumber = number
            )
        )
    }

    @PostMapping("number/{number}/withdraw")
    fun withdraw(
        @PathVariable number: Long,
        @RequestBody entryRequest: EntryRequest
    ) {
        logger.info("Received request to withdraw balance for wallet $number")

        walletService.withdraw(
            EntryCreateRequest(
                entryValue = entryRequest.entryValue,
                walletNumber = number
            )
        )
    }

    @PostMapping("number/{number}/transfer")
    fun transfer(
        @PathVariable number: Long,
        @RequestBody transferRequest: TransferRequest
    ) {

        logger.info("Received request to get transfer for wallet $number to ${transferRequest.walletNumber}")

        walletService.transfer(
            TransferCreateRequest(
                walletNumberOrigin = number,
                walletNumberDestination = transferRequest.walletNumber,
                value = transferRequest.value
            )
        )

    }

    companion object {
        private val logger = LoggerFactory.getLogger(WalletController::class.java)
    }

}