package com.reward.core.dto

import com.reward.core.domain.RewardPublish

data class RewardPublishResponse(
    val memberId: Long,
    val amount: Int,
    val winningCount: Int
) {
    companion object {
        internal fun of(rewardPublish: RewardPublish) = RewardPublishResponse(
            memberId = rewardPublish.memberId,
            amount = rewardPublish.amount,
            winningCount = rewardPublish.winningCount
        )
    }
}