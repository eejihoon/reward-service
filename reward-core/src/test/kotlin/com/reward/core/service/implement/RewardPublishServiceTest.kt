package com.reward.core.service.implement

import com.reward.core.domain.Reward
import com.reward.core.domain.RewardPublish
import com.reward.core.domain.RewardPublishRepository
import com.reward.core.domain.RewardRepository
import com.reward.core.dto.OrderCondition
import com.reward.core.dto.RewardPublishRequest
import com.reward.core.dto.RewardWinnersRequest
import com.reward.core.exception.AlreadyGetRewardException
import com.reward.core.exception.RewardErrorCode
import com.reward.core.exception.RewardExhaustedException
import com.reward.core.exception.RewardNotFoundException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
internal class RewardPublishServiceTest {

    @MockK
    private lateinit var publishRepository: RewardPublishRepository

    @MockK
    private lateinit var rewardRepository: RewardRepository

    @InjectMockKs
    private lateinit var sut: RewardPublishService

    @Nested
    inner class `보상금 지급 테스트`() {
        @Test
        fun `존재하지 않는 Reward 발행 요청 시 RewardNotFoundException 발생한다`() {
            //given
            val request = RewardPublishRequest(memberId = 1, rewardId = 1)
            every { rewardRepository.findByIdOrNull(any()) } returns null

            //when
            val actualException = assertThatThrownBy {
                sut.publish(request = request)
            }

            actualException
                .isInstanceOf(RewardNotFoundException::class.java)
                .hasMessageContaining(RewardErrorCode.REWARD_NOT_FOUND.message)
        }

        @Test
        fun `보상금 할당량을 모두 지급한 경우 400 RewardExhaustedException`() {
            val request = RewardPublishRequest(memberId = 1, rewardId = 1)
            val reward = mockk<Reward>()
            val rewardPublishHistories = mutableListOf<RewardPublish>()
            repeat(10) { rewardPublishHistories.add(mockk()) }

            every { rewardRepository.findByIdOrNull(any()) } returns reward
            every {
                publishRepository.findAllByRewardIdAndPublishedAt(rewardId = 1, publishedAt = LocalDate.now())
            } returns rewardPublishHistories
            every { reward.count } returns 10

            val actualException = assertThatThrownBy { sut.publish(request) }

            actualException
                .isInstanceOf(RewardExhaustedException::class.java)
                .hasMessageContaining(RewardErrorCode.REWARD_EXHAUSTED.message)
        }

        @Test
        fun `보상금을 이미 할당받았다면 400 AlreadyGetRewardException()`() {
            val request = RewardPublishRequest(memberId = 1, rewardId = 1)
            val reward = mockk<Reward>()
            val rewardPublishHistories = mutableListOf<RewardPublish>()
            repeat(10) { rewardPublishHistories.add(mockk()) }

            every { rewardRepository.findByIdOrNull(any()) } returns reward
            every {
                publishRepository.findAllByRewardIdAndPublishedAt(rewardId = 1, publishedAt = LocalDate.now())
            } returns rewardPublishHistories
            every { reward.count } returns 15
            every { rewardPublishHistories[0].memberId } returns request.memberId

            val actualException = assertThatThrownBy {
                sut.publish(request)
            }

            actualException
                .isInstanceOf(AlreadyGetRewardException::class.java)
                .hasMessageContaining(RewardErrorCode.ALREADY_GET_REWARD.message)
        }

        @ParameterizedTest
        @ValueSource(ints = [2, 4, 9]) // 전날 당첨된 경우 1을 더한 다음 계산하기 때문에 1씩 작은 수를 입력
        fun `3일, 5일, 10일 연속일 경우 각각 300, 500, 1000포인트 지급한다`(value: Int) {
            val request = RewardPublishRequest(memberId = 1, rewardId = 1)
            val reward = mockk<Reward>()
            val rewardPublish = RewardPublish(
                memberId = Long.MAX_VALUE,
                reward = reward,
                amount = 100,
                winningCount = value
            )
            val publishHistories = listOf(rewardPublish)


            every { rewardRepository.findByIdOrNull(any()) } returns reward
            every {
                publishRepository.findAllByRewardIdAndPublishedAt(rewardId = 1, publishedAt = LocalDate.now())
            } returns publishHistories
            every { reward.count } returns 15
            every { reward.rewardAmount } returns 100
            every { publishRepository.findByRewardIdAndMemberIdAndPublishedAt(
                any(), any(), any()
            ) } returns rewardPublish
            every { publishRepository.save(any()) } returns rewardPublish

            val actual = sut.publish(request)

            assertThat(actual.amount).isEqualTo(100 * (value+1))
            assertThat(actual.winningCount).isEqualTo(value+1)
        }

        @Test
        fun `보상금 지급 가능 상태라면 지급된 보상금 반환한다`() {
            val request = RewardPublishRequest(memberId = 1, rewardId = 1)
            val reward = mockk<Reward>()
            val rewardPublish = RewardPublish(
                memberId = Long.MAX_VALUE,
                reward = reward,
                amount = 100,
                winningCount = 0
            )
            val publishHistories = listOf(rewardPublish)


            every { rewardRepository.findByIdOrNull(any()) } returns reward
            every {
                publishRepository.findAllByRewardIdAndPublishedAt(rewardId = 1, publishedAt = LocalDate.now())
            } returns publishHistories
            every { reward.count } returns 15
            every { reward.rewardAmount } returns 100
            every { publishRepository.findByRewardIdAndMemberIdAndPublishedAt(
                any(), any(), any()
            ) } returns rewardPublish
            every { publishRepository.save(any()) } returns rewardPublish

            val actual = sut.publish(request)

            assertThat(actual.amount).isEqualTo(100)
            assertThat(actual.winningCount).isEqualTo(1)
        }
    }

    @Nested
    inner class `보상금 조회 테스트` {

        @Test
        fun `정렬 조건 Ascending 일 경우 id 오름차순으로 정렬해서 반환한다`() {
            val items = createRewardPublishHistories()
            every {
                publishRepository.findAllByRewardIdAndPublishedAt(any(), any())
            } returns items

            //when
            val actualResult = sut.getRewardWinners(
                RewardWinnersRequest(
                    rewardId = 1,
                    publishedAt = LocalDate.now(),
                    OrderCondition.ASC
                )
            )

            //then
            actualResult.forEachIndexed { index, item ->
                assertThat(item.publishId).isEqualTo((index+1).toLong())
            }
        }

        @Test
        fun `정렬 조건 Descending 일 경우 id 내림차순으로 정렬해서 반환한다`() {
            //given
            val items = createRewardPublishHistories()

            every {
                publishRepository.findAllByRewardIdAndPublishedAt(any(), any())
            } returns items

            //when
            val actualResult = sut.getRewardWinners(
                RewardWinnersRequest(
                    rewardId = 1,
                    publishedAt = LocalDate.now(),
                    OrderCondition.DESC
                )
            )

            //then
            actualResult.forEachIndexed { index, item ->
                assertThat(item.publishId).isEqualTo((10-index).toLong())
            }

        }

        private fun createRewardPublishHistories(): List<RewardPublish> {
            val items = mutableListOf<RewardPublish>()
            repeat(10) {
                val rewardPublish = mockk<RewardPublish>()
                val reward = mockk<Reward>()
                every { rewardPublish.id } returns (it+1).toLong()
                every { rewardPublish.memberId } returns (it+1).toLong()
                every { rewardPublish.reward } returns reward
                every { rewardPublish.publishedAt } returns LocalDate.now()
                every { rewardPublish.amount } returns 1000
                every { rewardPublish.winningCount } returns 1
                every { reward.id } returns it.toLong()
                items.add(rewardPublish)
            }
            return items
        }
    }

}