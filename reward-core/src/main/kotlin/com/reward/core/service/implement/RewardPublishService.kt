package com.reward.core.service.implement

import com.reward.core.domain.Reward
import com.reward.core.domain.RewardPublish
import com.reward.core.domain.RewardPublishRepository
import com.reward.core.domain.RewardRepository
import com.reward.core.dto.*
import com.reward.core.exception.AlreadyGetRewardException
import com.reward.core.exception.RewardErrorCode
import com.reward.core.exception.RewardExhaustedException
import com.reward.core.exception.RewardNotFoundException
import com.reward.core.redis.util.DistributeLock
import com.reward.core.redis.util.LockKey
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
internal class RewardPublishService(
    private val publishRepository: RewardPublishRepository,
    private val rewardRepository: RewardRepository,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    @DistributeLock(key = LockKey.REWARD_PUBLISH)
    fun publish(request: RewardPublishRequest): RewardPublishResponse {
        val reward = rewardRepository.findByIdOrNull(request.rewardId)
            ?: throw RewardNotFoundException(RewardErrorCode.REWARD_NOT_FOUND)

        // 오늘 날짜로 지급된 보상금을 모두 조회
        val today = LocalDate.now()
        val publishHistories = getAllPublishedToday(request, today)

        // 하루 보상금 할달량을 모두 지급한 경우
        if (publishHistories.size >= reward.count)
            throw RewardExhaustedException(RewardErrorCode.REWARD_EXHAUSTED)

        // 이미 오늘 날짜로 지급 받은 경우
        if (publishHistories.any { it.memberId == request.memberId })
            throw AlreadyGetRewardException(RewardErrorCode.ALREADY_GET_REWARD)

        // 보상금을 지급 받으려는 유저가 어제도 지급 받은 내역이 있는지 확인
        val yesterdayPublished = getAllPublishedYesterday(request, today)

        // 어제도 지급 받은 내역이 있다면 연속 지급일(winningCount) + 1, 없다면 1
        val winningCount = yesterdayPublished?.let { it.winningCount + 1 } ?: 1

        // 3일 연속, 5일 연속, 10일 연속이라면 그에 맞게 금액 추가
        val amount = calculateAmount(reward.rewardAmount, winningCount)

        // 지급
        val newRewardPublish = createRewardPublish(
            request = request,
            reward = reward,
            amount = amount,
            winningCount = winningCount
        )

        publishRepository.save(newRewardPublish)

        return RewardPublishResponse.of(newRewardPublish)
    }

    fun getRewardWinners(request: RewardWinnersRequest): List<RewardWinnerResponse> {
        // 지정한 날짜에 보상금 지급받은 유저 조회
        var rewardWinners = publishRepository.findAllByRewardIdAndPublishedAt(
            rewardId = request.rewardId,
            publishedAt = request.publishedAt,
        )

        rewardWinners = if (request.orderBy == OrderCondition.DESC)
            rewardWinners.sortedByDescending { it.id }
        else
            rewardWinners.sortedBy { it.id }

        return rewardWinners.map { RewardWinnerResponse.of(it) }
    }

    private fun getAllPublishedToday(
        request: RewardPublishRequest,
        today: LocalDate,
    ) = publishRepository.findAllByRewardIdAndPublishedAt(
        rewardId = request.rewardId,
        publishedAt = today
    )


    private fun getAllPublishedYesterday(
        request: RewardPublishRequest,
        today: LocalDate,
    ) = publishRepository.findByRewardIdAndMemberIdAndPublishedAt(
        rewardId = request.rewardId,
        memberId = request.memberId,
        publishedAt = today.minusDays(1)
    )

    private fun createRewardPublish(
        request: RewardPublishRequest,
        reward: Reward,
        amount: Int,
        winningCount: Int,
    ) = RewardPublish(
        memberId = request.memberId,
        reward = reward,
        amount = amount,
        winningCount = winningCount
    )

    private fun calculateAmount(rewardAmount: Int, winningCount: Int) = when (winningCount) {
        3 -> rewardAmount * winningCount
        5 -> rewardAmount * winningCount
        10 -> rewardAmount * winningCount
        else -> rewardAmount
    }

}