package com.reward.core.domain

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
internal class Coupon(
    publishedAt: LocalDate,
    expiredAt: LocalDateTime,
    couponEvent: CouponEvent,
): BaseEntity() {

    val publishedAt: LocalDate = publishedAt

    val expiredAt: LocalDateTime = expiredAt

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couponEvent_id", unique = false, nullable = false)
    val couponEvent = couponEvent

    var memberId: Long? = null
}

