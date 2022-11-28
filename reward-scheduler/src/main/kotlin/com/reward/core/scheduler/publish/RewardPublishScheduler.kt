package com.reward.core.scheduler.publish

import com.reward.core.dto.RewardPublishRequest
import com.reward.core.service.RewardService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import java.util.UUID

//@Component
class RewardPublishScheduler(
    private val rewardService: RewardService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * DAILY 보상금 발행
     */
    @Scheduled(cron = "\${schedules.cron.reward.publish}") // 매일 3시, 0시 30분
    fun publishReward() {
        log.info("START === PUBLISH::REWARD::EVENT::DAILY::SCHEDULER ${UUID.randomUUID()}")

//        rewardService.publish(
//            request = RewardPublishRequest(
//                publishCycle = PublishCycle.DAILY
//            )
//        )

        log.info("END === PUBLISH::REWARD::EVENT::DAILY::SCHEDULER ${UUID.randomUUID()}")
    }
}