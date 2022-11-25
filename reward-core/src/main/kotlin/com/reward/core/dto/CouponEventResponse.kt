package com.reward.core.dto

import com.reward.core.domain.CouponEvent
import com.reward.core.domain.DiscountPolicy
import com.reward.core.domain.PublishCycle
import java.time.LocalDateTime

data class CouponEventResponse private constructor(
    val id: Long,
    val title: String,
    val discountPolicy: DiscountPolicy,
    val discountRate: Int,
    val publishCycle: PublishCycle,
    val count: Int,
    val expiredDays: Long = 365,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime
) {

    companion object {
        internal fun of(couponEvent: CouponEvent) = CouponEventResponse(
            id = couponEvent.id,
            title = couponEvent.title,
            discountPolicy = couponEvent.getDiscountPolicy(),
            discountRate = couponEvent.getDiscountRate(),
            publishCycle = couponEvent.publishCycle,
            count = couponEvent.count,
            expiredDays = couponEvent.expiredDays,
            startDateTime = couponEvent.startDateTime,
            endDateTime = couponEvent.endDateTime
        )
    }
}