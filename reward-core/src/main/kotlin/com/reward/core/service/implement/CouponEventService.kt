package com.reward.core.service.implement

import com.reward.core.domain.CouponEventRepository
import com.reward.core.dto.CouponEventCreateRequest
import com.reward.core.dto.CouponEventResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CouponEventService(
    private val eventRepository: CouponEventRepository
) {

    @Transactional
    fun createCouponEvent(
        request: CouponEventCreateRequest
    ): CouponEventResponse {
        val event = eventRepository.save(request.toEntity())

        return CouponEventResponse.of(event)
    }
}