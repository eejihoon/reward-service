package com.reward.core.dto

import com.reward.core.domain.RewardEvent
import com.reward.core.domain.PublishCycle
import java.time.LocalDateTime

data class RewardEventResponse private constructor(
    val id: Long,
    val title: String,
    val rewardAmount: Int,
    val publishCycle: PublishCycle,
    val count: Int,
    val expiredDays: Long = 365,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime
) {

    companion object {
        internal fun of(rewardEvent: RewardEvent) = RewardEventResponse(
            id = rewardEvent.id,
            title = rewardEvent.title,
            rewardAmount = rewardEvent.rewardAmount,
            publishCycle = rewardEvent.publishCycle,
            count = rewardEvent.count,
            expiredDays = rewardEvent.expiredDays,
            startDateTime = rewardEvent.startDateTime,
            endDateTime = rewardEvent.endDateTime
        )
    }
}