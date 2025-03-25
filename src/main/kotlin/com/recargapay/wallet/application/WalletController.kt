package com.recargapay.wallet.application

import com.recargapay.wallet.application.request.EntryRequest
import com.recargapay.wallet.application.request.WalletRequest
import com.recargapay.wallet.application.response.BalanceResponse
import com.recargapay.wallet.application.response.WalletResponse
import com.recargapay.wallet.domain.entity.Entry
import com.recargapay.wallet.domain.entity.request.EntryCreateRequest
import com.recargapay.wallet.domain.entity.request.WalletCreateRequest
import com.recargapay.wallet.domain.service.WalletService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
        return ResponseEntity.ok(BalanceResponse(walletService.balance(number)))
    }

    @GetMapping("number/{number}/statement")
    fun statement(){}

    @PostMapping("number/{number}/credit")
    fun credit(
        @PathVariable number: Long,
        @RequestBody entryRequest: EntryRequest
    ) {
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
        walletService.withdraw(
            EntryCreateRequest(
                entryValue = entryRequest.entryValue,
                walletNumber = number
            )
        )
    }

    @PostMapping("number/{number}/transfer")
    fun transfer() {}

    companion object {
        private val logger = LoggerFactory.getLogger(WalletController::class.java)
    }


}