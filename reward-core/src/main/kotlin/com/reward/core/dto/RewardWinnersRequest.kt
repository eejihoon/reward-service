package com.reward.core.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

class RewardWinnersRequest(
    val rewardId: Long,
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    val publishedAt: LocalDate,
    val orderBy: OrderCondition
)

enum class OrderCondition {
    ASC, DESC
}