package com.recargapay.wallet.application.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class AuditLogger(
   private val logger: Class<*>
) {

    private val auditLogger: Logger = LoggerFactory.getLogger(logger)

    fun audit(action: Any, wallet: Any, value: Any, operation: Any) {
        val message = "[Audit] Wallet: $wallet, Action: $action, Opearation: $operation, Value: $value"
        auditLogger.info(message)
    }

}