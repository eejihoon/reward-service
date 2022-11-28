package com.reward.core.dto

import com.reward.core.domain.Reward
import java.time.LocalDateTime

data class RewardCreateRequest(
    val title: String,
    val description: String,
    val rewardAmount: Int,
    val count: Int,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime

) {

    internal fun toEntity() = Reward(
        title = title,
        description = description,
        rewardAmount = rewardAmount,
        count = count,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
    )

}