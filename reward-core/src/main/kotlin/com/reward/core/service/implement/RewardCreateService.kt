package com.reward.core.service.implement

import com.reward.core.domain.RewardRepository
import com.reward.core.dto.RewardCreateRequest
import com.reward.core.dto.RewardViewResponse
import org.springframework.stereotype.Service

@Service
internal class RewardCreateService(
    private val rewardRepository: RewardRepository
) {

    fun createReward(request: RewardCreateRequest): RewardViewResponse {
        val reward = rewardRepository.save(request.toEntity())
        return RewardViewResponse.of(reward)
    }
}