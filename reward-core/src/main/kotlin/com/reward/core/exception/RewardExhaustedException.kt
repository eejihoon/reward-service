package com.reward.core.exception

import com.reward.core.common.ErrorCode
import com.reward.core.common.RewardServiceRuntimeException

/**
 * 모두 소진된 보상금을 요청했을 때 발생하는 예외
 */
class RewardExhaustedException(
    code: ErrorCode
): RewardServiceRuntimeException(code)