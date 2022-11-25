package com.reward.core.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

internal interface CouponEventRepository: JpaRepository<CouponEvent, Long> {
    @Query(value = "SELECT couponEvent " +
            "FROM CouponEvent couponEvent " +
            "WHERE couponEvent.publishCycle = :publishCycle " +
            "AND :dateTime BETWEEN couponEvent.startDateTime AND couponEvent.endDateTime"
    )
    fun findAllPublishCycleAndBetweenStartDateTimeAndEndDateTime(
        publishCycle: PublishCycle,
        dateTime: LocalDateTime
    ): List<CouponEvent>
}