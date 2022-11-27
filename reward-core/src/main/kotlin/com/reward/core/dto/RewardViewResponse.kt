package com.reward.core.dto

import com.reward.core.domain.Reward

data class RewardViewResponse(
    val id: Long,
    val title: String,
    val description: String,
) {
    companion object {
        internal fun of(reward: Reward) = RewardViewResponse(
            id = reward.id,
            title = reward.title,
            description = reward.description
        )
    }
}