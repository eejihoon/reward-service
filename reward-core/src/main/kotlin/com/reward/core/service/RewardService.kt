package com.reward.core.service

import com.reward.core.dto.RewardCreateRequest
import com.reward.core.dto.RewardPublishRequest
import com.reward.core.dto.RewardPublishResponse
import com.reward.core.dto.RewardViewResponse

interface RewardService {
    /**
     * 리워드 발행
     */
    fun publish(request: RewardPublishRequest): RewardPublishResponse
    fun getReward(rewardId: Long): RewardViewResponse
    fun createReward(request: RewardCreateRequest): RewardViewResponse

}