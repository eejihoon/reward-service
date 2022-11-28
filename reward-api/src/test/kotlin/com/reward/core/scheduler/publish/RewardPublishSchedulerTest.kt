//package com.reward.core.scheduler.publish
//
//import com.reward.core.service.RewardService
//import org.assertj.core.api.Assertions.assertThat
//import org.awaitility.Awaitility
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import java.time.LocalDateTime
//import java.util.concurrent.TimeUnit
//
//@SpringBootTest(
//    properties = [
//        "schedules.cron.reward.publish=0/2 * * * * ?",
//    ]
//)
//class RewardPublishSchedulerTest {
//
//    @Autowired
//    private lateinit var rewardService: RewardService
//
//    private lateinit var event: RewardEventResponse
//
//    @BeforeEach
//    fun setup() {
//        event = rewardService.createEvent(
//            request = RewardEventCreateRequest(
//                title = "Test-EVENT",
//                rewardAmount = 500,
//                publishCycle = PublishCycle.DAILY,
//                count = 10,
//                startDateTime = LocalDateTime.now().minusDays(10),
//                endDateTime = LocalDateTime.now().plusDays(10),
//            )
//        )
//    }
//
//    @Test
//    fun `이틀 치 리워드를 미리 발행하는 스케줄러 동작하면 내일, 모레 날짜로 각각 10개씩 총 20개 발행한다`() {
//        val beforeRewards = rewardService.getRewards(event.id)
//
//        assertThat(beforeRewards.size).isEqualTo(0)
//
//        Awaitility.await()
//            .atMost(3, TimeUnit.SECONDS)
//            .untilAsserted {
//                val afterRewards = rewardService.getRewards(event.id)
//                assertThat(afterRewards.isNotEmpty()).isTrue
//                assertThat(afterRewards.size).isEqualTo(20)
//            }
//
//    }
//}