package com.reward.core.service.implement

import com.reward.core.domain.Reward
import com.reward.core.domain.RewardRepository
import com.reward.core.exception.RewardErrorCode
import com.reward.core.exception.RewardNotFoundException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class RewardFindServiceTest {

    @MockK
    private lateinit var rewardRepository: RewardRepository

    @InjectMockKs
    private lateinit var sut: RewardFindService

    @Test
    fun `존재하지 않는 reward 조회 시 404 RewardNotFoundException 발생한다`() {
        every { rewardRepository.findByIdOrNull(any()) } returns null

        val actualException = assertThatThrownBy {
            sut.getReward(rewardId = 10)
        }

        actualException
            .isInstanceOf(RewardNotFoundException::class.java)
            .hasMessageContaining(RewardErrorCode.REWARD_NOT_FOUND.message)
    }

    @Test
    fun `Reward 조회 시 RewardViewResponse 반환한다`() {
        val reward = Reward(
            title = "test-title", description = "desc", rewardAmount = 100,
            count = 10, startDateTime = LocalDateTime.now(), endDateTime = LocalDateTime.now()
        )
        every { rewardRepository.findByIdOrNull(any()) } returns reward

        val actualResult = sut.getReward(rewardId = reward.id)

        assertThat(actualResult.id).isEqualTo(reward.id)
        assertThat(actualResult.title).isEqualTo(reward.title)
        assertThat(actualResult.description).isEqualTo(reward.description)
    }
}