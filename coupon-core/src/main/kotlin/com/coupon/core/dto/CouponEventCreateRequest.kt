package com.coupon.core.dto

import com.coupon.core.domain.CouponEvent
import com.coupon.core.domain.DiscountPolicy
import com.coupon.core.domain.PublishCycle
import java.time.LocalDateTime

data class CouponEventCreateRequest(
    val title: String,
    val discountPolicy: DiscountPolicy,
    val discountRate: Int,
    val publishCycle: PublishCycle,
    val count: Int,
    val expiredDays: Long = 365,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime
) {

    internal fun toEntity() = CouponEvent(
        title = title,
        discountPolicy = discountPolicy,
        discountRate = discountRate,
        publishCycle = publishCycle,
        count = count,
        expiredDays = expiredDays,
        startDateTime = startDateTime,
        endDateTime = endDateTime
    )
}