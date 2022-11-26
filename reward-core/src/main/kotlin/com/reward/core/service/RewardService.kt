package com.reward.core.service

import com.reward.core.dto.RewardPublishRequest

interface RewardService {
    /**
     * 리워드 발행
     */
    fun publish(request: RewardPublishRequest)

}