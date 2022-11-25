package com.reward.core.service

import com.reward.core.dto.CouponEventCreateRequest
import com.reward.core.dto.CouponEventResponse
import com.reward.core.dto.CouponPublishRequest
import com.reward.core.dto.CouponResponse

interface CouponService {
    fun publish(request: CouponPublishRequest)
    fun createEvent(request: CouponEventCreateRequest): CouponEventResponse
    fun getCoupons(eventId: Long): List<CouponResponse>

}

