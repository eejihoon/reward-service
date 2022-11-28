package order.api.common

import com.reward.core.common.ErrorCode

enum class CommonErrorCode(
    override val status: Int,
    override val message: String
): ErrorCode {
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR"),
    BAD_REQUEST(400, "BAD REQUEST")
}