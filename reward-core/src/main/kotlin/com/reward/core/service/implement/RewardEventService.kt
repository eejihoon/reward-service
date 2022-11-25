package com.reward.core.service.implement

import com.reward.core.domain.RewardEventRepository
import com.reward.core.dto.RewardEventCreateRequest
import com.reward.core.dto.RewardEventResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class RewardEventService(
    private val eventRepository: RewardEventRepository
) {

    @Transactional
    fun createRewardEvent(
        request: RewardEventCreateRequest
    ): RewardEventResponse {
        val event = eventRepository.save(request.toEntity())

        return RewardEventResponse.of(event)
    }
}