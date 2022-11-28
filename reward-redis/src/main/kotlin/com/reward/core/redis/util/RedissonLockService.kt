package com.reward.core.redis.util

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*

@Order(1)
@Aspect
@Component
internal class RedissonLockAop(
    private val redissonClient: RedissonClient
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Around("@annotation(com.reward.core.redis.util.DistributeLock)")
    fun tryLock(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val lockAnnotation = method.getAnnotation(DistributeLock::class.java)
        val key = lockAnnotation.key
        val lock = redissonClient.getLock(key.name)

        try {
            val lockable = lock.tryLock(lockAnnotation.waitTime, lockAnnotation.leaseTime, lockAnnotation.timeUnit)
            if (!lockable) return false
            log.info("KEY: {} LOCK: {}", key, signature)
            return joinPoint.proceed()
        } catch (e: Exception) {
            log.error("redissonError", e)
            throw e
        } finally {
            if (lock.isLocked && lock.isHeldByCurrentThread) {
                log.info("key {} UNLOCK: {}", key, signature)
                lock.unlock()
            }
        }
    }

}