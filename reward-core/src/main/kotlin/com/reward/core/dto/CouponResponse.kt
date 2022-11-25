package com.reward.core.dto

import com.reward.core.domain.Coupon
import java.time.LocalDate
import java.time.LocalDateTime

data class CouponResponse private constructor(
    val publishedAt: LocalDate,
    val expiredAt: LocalDateTime,
    val couponEventResponse: CouponEventResponse,
    val memberId: Long? = null
) {
    companion object {
        internal fun of(coupon: Coupon) = CouponResponse(
            publishedAt = coupon.publishedAt,
            expiredAt = coupon.expiredAt,
            couponEventResponse = CouponEventResponse.of(coupon.couponEvent),
        )
    }
}
