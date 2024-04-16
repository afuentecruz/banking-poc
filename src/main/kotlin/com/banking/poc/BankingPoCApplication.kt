package com.banking.poc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankingPoCApplication

fun main(args: Array<String>) {
    runApplication<BankingPoCApplication>(*args)
}
