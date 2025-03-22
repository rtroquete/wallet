package com.recargapay.wallet.application

import com.recargapay.wallet.application.request.UserRequest
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("client")
class UserController {

    @PostMapping
    fun create(userRequest: UserRequest) {
        logger.info("teste")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UserController::class.java)
    }

}