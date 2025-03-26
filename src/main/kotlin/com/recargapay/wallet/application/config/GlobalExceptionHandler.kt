package com.recargapay.wallet.application.config

import com.recargapay.wallet.domain.exception.InsufficientBalanceException
import com.recargapay.wallet.domain.exception.UserWalletException
import com.recargapay.wallet.domain.exception.WalletNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserWalletException::class)
    fun handleUserWalletException(ex: UserWalletException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("USER_WALLET_ERROR", ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(InsufficientBalanceException::class)
    fun handleInsufficientBalanceException(ex: InsufficientBalanceException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("INSUFFICIENT_BALANCE_ERROR", ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(WalletNotFoundException::class)
    fun handleWalletNotFoundException(ex: WalletNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("WALLET_NOT_FOUND", ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

}

data class ErrorResponse(
    val errorCode: String,
    val message: String
)