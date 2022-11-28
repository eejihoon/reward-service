package com.reward.core.dto

import java.time.LocalDate

class RewardWinnersRequest(
    val rewardId: Long,
    val publishedAt: LocalDate,
    val orderBy: OrderCondition
)

enum class OrderCondition {
    ASC, DESC
}