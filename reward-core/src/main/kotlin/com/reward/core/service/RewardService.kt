package com.reward.core.service

import com.reward.core.dto.*

interface RewardService {
    /**
     * 리워드 발행
     */
    fun publish(request: RewardPublishRequest): RewardPublishResponse
    fun getReward(rewardId: Long): RewardViewResponse
    fun createReward(request: RewardCreateRequest): RewardViewResponse
    fun getRewardWinners(request: RewardWinnersRequest): List<RewardWinnerResponse>

}