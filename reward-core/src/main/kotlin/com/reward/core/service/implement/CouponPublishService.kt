package com.reward.core.service.implement

import com.reward.core.domain.Coupon
import com.reward.core.domain.CouponEventRepository
import com.reward.core.domain.CouponRepository
import com.reward.core.dto.CouponPublishRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
internal class CouponPublishService(
    private val couponRepository: CouponRepository,
    private val couponEventRepository: CouponEventRepository,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun publish(request: CouponPublishRequest) {
        repeat(request.publishCycle.days) {
            val plushDay = (it + 1).toLong()
            val publishDateTime = LocalDateTime.now().plusDays(plushDay)

            // 발행하는 날짜에 진행 중인 쿠폰 이벤트 조회
            val events =
                couponEventRepository.findAllPublishCycleAndBetweenStartDateTimeAndEndDateTime(
                    publishCycle = request.publishCycle,
                    dateTime = publishDateTime
                )

            events.forEach { event ->

                // 이벤트에 지정된 발행 갯수 만큼 생성
                repeat(event.count) { plusDay ->
                    val coupon = Coupon(
                        expiredAt = publishDateTime.plusDays(event.expiredDays),
                        couponEvent = event,
                        publishedAt = LocalDate.now().plusDays((plusDay+1).toLong())
                    )

                    couponRepository.save(coupon)
                }

                log.info("PUBLISH::COUPON:: ${event.title} 쿠폰 ${event.count}개 발행 완료")
            }
        }
    }

}