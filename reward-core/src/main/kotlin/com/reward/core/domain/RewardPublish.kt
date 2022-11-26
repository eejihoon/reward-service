package com.reward.core.domain

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
internal class RewardPublish(
    memberId: Long,
    reward: Reward,
    amount: Int,
    winningCount: Int,
): BaseEntity() {

    val memberId: Long = memberId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_id", unique = false, nullable = false)
    val reward: Reward = reward

    val amount: Int = amount

    /**
     * 연속 당첨 횟수
     */
    var winningCount: Int = winningCount


    val publishedAt: LocalDate = LocalDate.now()

    fun canPublished(size: Int) = this.reward.count >= size
}