package com.recargapay.wallet.application.config

import com.recargapay.wallet.domain.exception.UserWalletException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserWalletException::class)
    fun handleBadRequestException(ex: UserWalletException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("USER_WALLET_ERROR", ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}

data class ErrorResponse(
    val errorCode: String,
    val message: String
)