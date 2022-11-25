package com.reward.core.dto

import com.reward.core.domain.Reward
import java.time.LocalDate
import java.time.LocalDateTime

data class RewardResponse private constructor(
    val publishedAt: LocalDate,
    val expiredAt: LocalDateTime,
    val rewardEventResponse: RewardEventResponse,
    val memberId: Long? = null
) {
    companion object {
        internal fun of(reward: Reward) = RewardResponse(
            publishedAt = reward.publishedAt,
            expiredAt = reward.expiredAt,
            rewardEventResponse = RewardEventResponse.of(reward.rewardEvent),
        )
    }
}
