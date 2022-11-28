package com.reward.core.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
internal class Reward(
    title: String,
    description: String,
    rewardAmount: Int,
    count: Int,
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
): BaseEntity() {

    /**
     * 행사명
     */
    var title: String = title
        set(value) {
            if (value.isBlank()) return
            field = value
        }

    @Lob
    var description: String = description

    /**
     * 보상금액
     */
    var rewardAmount: Int = rewardAmount
        set(value) {
            if (field < 0) return
            field = value
        }

    /**
     * 보상금 지급 갯수
     */
    var count: Int = count
        set(value) {
            if (value < 0) return
            field = value
        }

    /**
     * 행사 시작일
     */
    var startDateTime = startDateTime
        set(value) {
            if (field.isAfter(LocalDateTime.now())) return
            field = value
        }

    /**
     * 행사 종료일
     */
    var endDateTime = endDateTime
        set(value) {
            if (field.isBefore(LocalDateTime.now())) return
            field = value
        }
}