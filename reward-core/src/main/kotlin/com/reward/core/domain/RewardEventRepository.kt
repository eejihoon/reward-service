package com.reward.core.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

internal interface RewardEventRepository: JpaRepository<RewardEvent, Long> {
    @Query(value = "SELECT rewardEvent " +
            "FROM RewardEvent rewardEvent " +
            "WHERE rewardEvent.publishCycle = :publishCycle " +
            "AND :dateTime BETWEEN rewardEvent.startDateTime AND rewardEvent.endDateTime"
    )
    fun findAllPublishCycleAndBetweenStartDateTimeAndEndDateTime(
        publishCycle: PublishCycle,
        dateTime: LocalDateTime
    ): List<RewardEvent>
}