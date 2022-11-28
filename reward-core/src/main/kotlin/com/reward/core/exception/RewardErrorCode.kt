package com.reward.core.exception

import com.reward.core.common.ErrorCode

enum class RewardErrorCode(
    override val status: Int,
    override val message: String,
): ErrorCode {
    REWARD_NOT_FOUND(404, "존재하지 않은 리워드"),
    REWARD_EXHAUSTED(400, "보상금 모두 소진됨"),
    ALREADY_GET_REWARD(400, "이미 수령한 보상금")
}