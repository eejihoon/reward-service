package com.reward.core.service

import com.reward.core.dto.RewardPublishRequest
import com.reward.core.service.implement.RewardPublishService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
internal class DefaultRewardService(
    private val publishService: RewardPublishService,
): RewardService {

    @Transactional
    override fun publish(request: RewardPublishRequest) {
        publishService.publish(request = request)
    }

}