package com.reward.api

import com.reward.core.service.RewardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val GET_REWARD_REQUEST = "/v1/api/rewards"
@RestController
@RequestMapping(GET_REWARD_REQUEST)
class RewardFindApiController(
    private val rewardService: RewardService
) {

    @GetMapping("/{memberId}")
    fun getRewardAmount(
        @PathVariable memberId: String
    ) {

    }

}