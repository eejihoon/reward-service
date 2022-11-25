package com.reward.core.domain

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
internal class Reward(
    publishedAt: LocalDate,
    expiredAt: LocalDateTime,
    rewardEvent: RewardEvent,
): BaseEntity() {

    /**
     * 보상금 발행일
     */
    val publishedAt: LocalDate = publishedAt

    /**
     * 보상금 만료일
     */
    val expiredAt: LocalDateTime = expiredAt


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rewardEvent_id", unique = false, nullable = false)
    val rewardEvent = rewardEvent

    /**
     * 보상금 수령자
     */
    var memberId: Long? = null
}

