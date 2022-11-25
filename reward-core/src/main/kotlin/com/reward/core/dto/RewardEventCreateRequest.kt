package com.reward.core.dto

import com.reward.core.domain.RewardEvent
import com.reward.core.domain.PublishCycle
import java.time.LocalDateTime

data class RewardEventCreateRequest(
    val title: String,
    val rewardAmount: Int,
    val publishCycle: PublishCycle,
    val count: Int,
    val expiredDays: Long = 365,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime
) {

    internal fun toEntity() = RewardEvent(
        title = title,
        rewardAmount = rewardAmount,
        publishCycle = publishCycle,
        count = count,
        expiredDays = expiredDays,
        startDateTime = startDateTime,
        endDateTime = endDateTime
    )
}