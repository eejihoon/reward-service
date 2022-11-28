package com.reward.core.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

internal interface RewardPublishRepository: JpaRepository<RewardPublish, Long> {


    fun findAllByRewardIdAndPublishedAt(
        rewardId: Long,
        publishedAt: LocalDate
    ): List<RewardPublish>

    fun findByRewardIdAndMemberIdAndPublishedAt(rewardId: Long, memberId: Long, publishedAt: LocalDate): RewardPublish?
}