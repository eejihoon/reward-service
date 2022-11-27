package com.reward.core.service.implement

import com.reward.core.domain.RewardRepository
import com.reward.core.dto.RewardViewResponse
import com.reward.core.exception.RewardErrorCode
import com.reward.core.exception.RewardNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
internal class RewardFindService(
    private val rewardRepository: RewardRepository
) {
    fun getReward(rewardId: Long): RewardViewResponse {
        val reward = rewardRepository.findByIdOrNull(rewardId)
            ?: throw RewardNotFoundException(RewardErrorCode.REWARD_NOT_FOUND)

        return RewardViewResponse.of(reward)
    }
}