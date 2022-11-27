package com.reward.core.dto

import org.jetbrains.annotations.NotNull
import javax.validation.constraints.Min


data class RewardPublishRequest(
    @field:NotNull
    @field:Min(value = 0)
    val memberId: Long,
    @field:NotNull
    @field:Min(value = 0)
    val rewardId: Long,
)
