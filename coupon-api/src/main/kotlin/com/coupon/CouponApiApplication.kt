package com.coupon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
class CouponApiApplication

fun main(args: Array<String>) {
	runApplication<CouponApiApplication>(*args)
}
