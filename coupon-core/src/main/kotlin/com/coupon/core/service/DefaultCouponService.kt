package com.coupon.core.service

import com.coupon.core.dto.CouponEventCreateRequest
import com.coupon.core.dto.CouponEventResponse
import com.coupon.core.dto.CouponPublishRequest
import com.coupon.core.dto.CouponResponse
import com.coupon.core.service.implement.CouponEventService
import com.coupon.core.service.implement.CouponFindService
import com.coupon.core.service.implement.CouponPublishService
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