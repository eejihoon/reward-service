package com.reward.core

import com.reward.core.domain.RewardRepository
import com.reward.core.dto.RewardCreateRequest
import com.reward.core.service.RewardService
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Component
internal class TestDataInitializer(
    private val rewardService: RewardService,
    private val rewardRepository: RewardRepository
) {
    @PostConstruct
    fun setup() {
        if (rewardRepository.count() > 0) return
        rewardService.createReward(
            RewardCreateRequest(
                title = "매일 00시 00분 00초 선착순 10명 100포인트 지급!!",
                description = "- 보상 지급 방식은 사용자가 받기를 누를 때 지급하게 됩니다.",
                rewardAmount = 100,
                count = 10,
                startDateTime = LocalDateTime.now().minusDays(10),
                endDateTime = LocalDateTime.now().plusYears(1)
            )
        )
    }
}