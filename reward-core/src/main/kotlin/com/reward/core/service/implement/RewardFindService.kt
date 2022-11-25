package com.reward.core.service.implement

import com.reward.core.domain.RewardRepository
import com.reward.core.dto.RewardResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class RewardFindService(
    private val rewardRepository: RewardRepository
) {

    @Transactional
    fun getRewards(eventId: Long): List<RewardResponse> {
        val rewards = rewardRepository.findAllByRewardEventId(rewardEventId = eventId)

        return rewards.map { RewardResponse.of(it) }
    }

}