package com.reward.core.service

import com.reward.core.dto.RewardEventCreateRequest
import com.reward.core.dto.RewardEventResponse
import com.reward.core.dto.RewardPublishRequest
import com.reward.core.dto.RewardResponse
import com.reward.core.service.implement.RewardEventService
import com.reward.core.service.implement.RewardFindService
import com.reward.core.service.implement.RewardPublishService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class DefaultRewardService(
    private val publishService: RewardPublishService,
    private val eventService: RewardEventService,
    private val findService: RewardFindService,
): RewardService {

    @Transactional
    override fun publish(request: RewardPublishRequest) {
        publishService.publish(request = request)
    }

    @Transactional
    override fun createEvent(request: RewardEventCreateRequest): RewardEventResponse {
        return eventService.createRewardEvent(request)
    }

    @Transactional(readOnly = true)
    override fun getRewards(eventId: Long): List<RewardResponse> {
        return findService.getRewards(eventId = eventId)
    }
}