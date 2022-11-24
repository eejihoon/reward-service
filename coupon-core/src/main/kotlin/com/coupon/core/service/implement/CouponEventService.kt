package com.coupon.core.service.implement

import com.coupon.core.domain.CouponEventRepository
import com.coupon.core.dto.CouponEventCreateRequest
import com.coupon.core.dto.CouponEventResponse
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