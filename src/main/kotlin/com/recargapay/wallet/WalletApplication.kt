package com.recargapay.wallet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class WalletApplication

fun main(args: Array<String>) {
	runApplication<WalletApplication>(*args)
}
