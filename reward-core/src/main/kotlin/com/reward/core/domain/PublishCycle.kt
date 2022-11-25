package com.reward.core.domain

/**
 * 발행 주기
 */
enum class PublishCycle(
    val days: Int   // 발행 사이클마다 며칠 치 쿠폰을 미리 발행할 것인지
) {
    DAILY(2), // 매일 2일치(내일, 모레) 쿠폰을 미리 발행
}