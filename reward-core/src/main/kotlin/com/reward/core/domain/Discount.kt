package com.reward.core.domain

import javax.persistence.Embeddable

@Embeddable
internal class Discount(
    type: DiscountPolicy,
    rate: Int,
) {
    var type = type
    var rate = rate
}

/**
 * 할인 정책(퍼센티지, 금액)
 */
enum class DiscountPolicy {
    PERCENT, AMOUNT
}