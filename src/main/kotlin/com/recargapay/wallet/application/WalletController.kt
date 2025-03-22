package com.recargapay.wallet.application

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("wallet")
class WalletController {

    @PostMapping
    fun create(){}

    @GetMapping
    fun balance(){}

    @PostMapping("{id}/credit")
    fun credit(){}

    @PostMapping("{id}/withdraw")
    fun withdraw() {}

    @PostMapping("{id}/transfer")
    fun transfer() {}


}