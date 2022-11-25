package com.reward.core.scheduler.publish

import com.reward.core.domain.DiscountPolicy
import com.reward.core.domain.PublishCycle
import com.reward.core.dto.CouponEventCreateRequest
import com.reward.core.dto.CouponEventResponse
import com.reward.core.service.CouponService
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@SpringBootTest(
    properties = [
        "schedules.cron.coupon.publish=0/2 * * * * ?",
    ]
)
class CouponPublishSchedulerTest {

    @Autowired
    private lateinit var couponService: CouponService

    private lateinit var event: CouponEventResponse

    @BeforeEach
    fun setup() {
        event = couponService.createEvent(
            request = CouponEventCreateRequest(
                title = "Test-EVENT",
                discountPolicy = DiscountPolicy.AMOUNT,
                discountRate = 500,
                publishCycle = PublishCycle.DAILY,
                count = 10,
                startDateTime = LocalDateTime.now().minusDays(10),
                endDateTime = LocalDateTime.now().plusDays(10),
            )
        )
    }

    @Test
    fun `이틀 치 쿠폰을 미리 발행하는 스케줄러 동작하면`() {
        val beforeCoupons = couponService.getCoupons(event.id)

        assertThat(beforeCoupons.size).isEqualTo(0)

        Awaitility.await()
            .atMost(3, TimeUnit.SECONDS)
            .untilAsserted {
                val afterCoupons = couponService.getCoupons(event.id)
                assertThat(afterCoupons.size).isEqualTo(event.count * event.publishCycle.days)
            }
    }
}