package com.coupon.core.service.implement

import com.coupon.core.domain.CouponRepository
import com.coupon.core.dto.CouponResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CouponFindService(
    private val couponRepository: CouponRepository
) {

    @Transactional
    fun getCoupons(eventId: Long): List<CouponResponse> {
        val coupons = couponRepository.findAllByCouponEventId(couponEventId = eventId)

        return coupons.map { CouponResponse.of(it) }
    }

}