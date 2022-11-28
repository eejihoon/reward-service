package com.reward.core.exception

import com.reward.core.common.ErrorCode
import com.reward.core.common.RewardServiceRuntimeException

class AlreadyGetRewardException(
    code: ErrorCode
): RewardServiceRuntimeException(code)