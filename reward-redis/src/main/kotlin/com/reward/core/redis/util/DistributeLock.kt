package com.reward.core.redis.util

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributeLock(
    val key: LockKey,
    val waitTime: Long = 4, // 대기 시간
    val leaseTime: Long = 2, // 락 점유 시간
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
)

enum class LockKey {
    REWARD_PUBLISH
}