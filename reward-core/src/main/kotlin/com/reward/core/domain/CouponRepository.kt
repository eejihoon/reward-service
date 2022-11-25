package com.reward.core.domain

import org.springframework.data.jpa.repository.JpaRepository

internal interface CouponRepository: JpaRepository<Coupon, Long> {
    fun findAllByCouponEventId(couponEventId: Long): List<Coupon>
}