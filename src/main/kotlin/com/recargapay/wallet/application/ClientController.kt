package com.recargapay.wallet.application

import com.recargapay.wallet.application.request.ClientRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("client")
class ClientController {

    @PostMapping
    fun create(clientRequest: ClientRequest) {}


}