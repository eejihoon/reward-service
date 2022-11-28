package com.reward.core.service.implement

import com.reward.core.domain.Reward
import com.reward.core.domain.RewardPublishRepository
import com.reward.core.domain.RewardRepository
import com.reward.core.dto.RewardPublishRequest
import com.reward.core.service.RewardService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@DisplayName("보상금 지급 동시성 테스트")
@SpringBootTest
internal class RewardPublishConcurrencyTest {

    @Autowired
    private lateinit var rewardService: RewardService

    @Autowired
    private lateinit var publishRepository: RewardPublishRepository

    @Autowired
    private lateinit var rewardRepository: RewardRepository

    private lateinit var reward: Reward

    @BeforeEach
    fun setup() {
        publishRepository.deleteAll()
        reward = rewardRepository.save(
            Reward(
                title = "test-title",
                description = "test-description",
                rewardAmount = 100,
                count = 10,
                startDateTime = LocalDateTime.now(),
                endDateTime = LocalDateTime.now()
            )
        )
    }

    @AfterEach
    fun tearDown() {
        publishRepository.deleteAll()
        rewardRepository.deleteAll()
    }

    @Test
    fun `스레드 100개가 동시에 보상금 지급 요청해도 정확히 10개만 발급한다`() {
        val threadNumber = 100
        val executorService = Executors.newFixedThreadPool(64)
        val latch = CountDownLatch(threadNumber)
        var memberId = 1L

        for (i in 0 until threadNumber) {
            executorService.submit {
                try {
                    rewardService.publish(request = RewardPublishRequest(memberId = memberId++, rewardId = reward.id))
                } finally {
                    latch.countDown()
                }
            }

        }

        latch.await()

        val publishHistories =
            publishRepository.findAllByRewardIdAndPublishedAt(rewardId = reward.id, LocalDate.now())

        assertThat(publishHistories.size).isEqualTo(10)
    }

}
