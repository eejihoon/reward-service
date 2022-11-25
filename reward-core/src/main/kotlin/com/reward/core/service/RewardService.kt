package com.reward.core.service

import com.reward.core.dto.RewardEventCreateRequest
import com.reward.core.dto.RewardEventResponse
import com.reward.core.dto.RewardPublishRequest
import com.reward.core.dto.RewardResponse

interface RewardService {
    /**
     * 리워드 발행
     */
    fun publish(request: RewardPublishRequest)

    /**
     * 리워드 이벤트 생성
     */
    fun createEvent(request: RewardEventCreateRequest): RewardEventResponse

    /**
     * 리워드 이벤트 ID로 발급된 모든 리워드 조회
     */
    fun getRewards(eventId: Long): List<RewardResponse>

}

