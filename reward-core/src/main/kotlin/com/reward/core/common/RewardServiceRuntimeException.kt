package com.reward.core.common

open class RewardServiceRuntimeException(
    val code: ErrorCode
): RuntimeException(code.message) {
}