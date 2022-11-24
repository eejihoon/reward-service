package com.coupon.core.scheduler.publish

import com.coupon.core.domain.PublishCycle
import com.coupon.core.dto.CouponPublishRequest
import com.coupon.core.service.CouponService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CouponPublishScheduler(
    private val couponService: CouponService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * DAILY 쿠폰 발행
     */
    @Scheduled(cron = "\${schedules.cron.coupon.publish}") // 매일 3시, 0시 30분
    fun publishCoupon() {
        log.info("START === PUBLISH::COUPON::EVENT::DAILY::SCHEDULER ${UUID.randomUUID()}")

        couponService.publish(
            request = CouponPublishRequest(
                publishCycle = PublishCycle.DAILY
            )
        )

        log.info("END === PUBLISH::COUPON::EVENT::DAILY::SCHEDULER ${UUID.randomUUID()}")
    }
}