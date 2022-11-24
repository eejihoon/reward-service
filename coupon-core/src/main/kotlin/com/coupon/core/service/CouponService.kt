package com.coupon.core.service

import com.coupon.core.dto.CouponEventCreateRequest
import com.coupon.core.dto.CouponEventResponse
import com.coupon.core.dto.CouponPublishRequest
import com.coupon.core.dto.CouponResponse

interface CouponService {
    fun publish(request: CouponPublishRequest)
    fun createEvent(request: CouponEventCreateRequest): CouponEventResponse
    fun getCoupons(eventId: Long): List<CouponResponse>

}

