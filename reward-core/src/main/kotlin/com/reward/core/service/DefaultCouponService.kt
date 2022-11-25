package com.reward.core.service

import com.reward.core.dto.CouponEventCreateRequest
import com.reward.core.dto.CouponEventResponse
import com.reward.core.dto.CouponPublishRequest
import com.reward.core.dto.CouponResponse
import com.reward.core.service.implement.CouponEventService
import com.reward.core.service.implement.CouponFindService
import com.reward.core.service.implement.CouponPublishService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class DefaultCouponService(
    private val publishService: CouponPublishService,
    private val eventService: CouponEventService,
    private val findService: CouponFindService,
): CouponService {

    @Transactional
    override fun publish(request: CouponPublishRequest) {
        publishService.publish(request = request)
    }

    @Transactional
    override fun createEvent(request: CouponEventCreateRequest): CouponEventResponse {
        return eventService.createCouponEvent(request)
    }

    @Transactional(readOnly = true)
    override fun getCoupons(eventId: Long): List<CouponResponse> {
        return findService.getCoupons(eventId = eventId)
    }
}