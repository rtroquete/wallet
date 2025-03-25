package com.recargapay.wallet.application

import com.recargapay.wallet.domain.service.WalletService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("wallet")
class WalletController(
    private val walletService: WalletService
) {

    @PostMapping
    fun create(){}

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


}