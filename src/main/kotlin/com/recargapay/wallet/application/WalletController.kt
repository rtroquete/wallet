package com.recargapay.wallet.application

import com.recargapay.wallet.application.request.WalletRequest
import com.recargapay.wallet.application.response.WalletResponse
import com.recargapay.wallet.domain.repository.WalletCreateRequest
import com.recargapay.wallet.domain.service.WalletService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
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
    fun create(@RequestBody request: WalletRequest): WalletResponse {
        logger.info("Received request to create wallet for ${request.userId}")
        return walletService.create(WalletCreateRequest(request.userId)).let {
            WalletResponse.fromDomain(it)
        }
    }

    @GetMapping("number/{number}/balance")
    fun balance(){}

    @GetMapping("number/{number}/statement")
    fun statement(){}

    @PostMapping("number/{number}/credit")
    fun credit(){}

    @PostMapping("number/{number}/withdraw")
    fun withdraw() {}

    @PostMapping("number/{number}/transfer")
    fun transfer() {}

    companion object {
        private val logger = LoggerFactory.getLogger(WalletController::class.java)
    }


}