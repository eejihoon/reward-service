package com.reward.core.service.implement

import com.reward.core.domain.Reward
import com.reward.core.domain.RewardEventRepository
import com.reward.core.domain.RewardRepository
import com.reward.core.dto.RewardPublishRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
internal class RewardPublishService(
    private val rewardRepository: RewardRepository,
    private val rewardEventRepository: RewardEventRepository,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun publish(request: RewardPublishRequest) {
        repeat(request.publishCycle.days) {
            val plushDay = (it + 1).toLong()
            val publishDateTime = LocalDateTime.now().plusDays(plushDay)

            // 발행하는 날짜에 진행 중인 리워드 이벤트 조회
            val events =
                rewardEventRepository.findAllPublishCycleAndBetweenStartDateTimeAndEndDateTime(
                    publishCycle = request.publishCycle,
                    dateTime = publishDateTime
                )

            events.forEach { event ->

                // 이벤트에 지정된 발행 갯수 만큼 생성
                repeat(event.count) { plusDay ->
                    val reward = Reward(
                        expiredAt = publishDateTime.plusDays(event.expiredDays),
                        rewardEvent = event,
                        publishedAt = LocalDate.now().plusDays((plusDay+1).toLong())
                    )

                    rewardRepository.save(reward)
                }

                log.info("PUBLISH::REWARD::${event.title} 쿠폰 ${event.count}개 발행 완료")
            }
        }
    }

}