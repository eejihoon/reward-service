package com.reward.core.exception

import com.reward.core.common.ErrorCode
import com.reward.core.common.RewardServiceRuntimeException

class RewardNotFoundException(
    code: ErrorCode
): RewardServiceRuntimeException(code) {
}