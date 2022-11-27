package com.reward.core.service

import com.reward.core.dto.RewardCreateRequest
import com.reward.core.dto.RewardPublishRequest
import com.reward.core.dto.RewardPublishResponse
import com.reward.core.dto.RewardViewResponse
import com.reward.core.service.implement.RewardCreateService
import com.reward.core.service.implement.RewardFindService
import com.reward.core.service.implement.RewardPublishService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
internal class DefaultRewardService(
    private val publishService: RewardPublishService,
    private val rewardFindService: RewardFindService,
    private val rewardCreateService: RewardCreateService
): RewardService {

    @Transactional
    override fun publish(request: RewardPublishRequest): RewardPublishResponse {
        return publishService.publish(request = request)
    }

    @Transactional(readOnly = true)
    override fun getReward(rewardId: Long): RewardViewResponse {
        return rewardFindService.getReward(rewardId = rewardId)
    }

    @Transactional
    override fun createReward(
        request: RewardCreateRequest
    ): RewardViewResponse {
        return  rewardCreateService.createReward(request)
    }

}