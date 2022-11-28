package com.reward.core.dto

import com.reward.core.domain.RewardPublish

data class RewardWinnerResponse private constructor(
    val publishId: Long,
    val rewardId: Long,
    val memberId: Long,
    val winningCount: Int,
    val amount: Int,
) {
    companion object {
        internal fun of(rewardPublish: RewardPublish) = RewardWinnerResponse(
            publishId = rewardPublish.id,
            rewardId = rewardPublish.reward.id,
            memberId = rewardPublish.memberId,
            winningCount = rewardPublish.winningCount,
            amount = rewardPublish.amount
        )
    }
}