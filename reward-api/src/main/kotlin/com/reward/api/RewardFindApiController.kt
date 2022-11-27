package com.reward.api

import com.reward.core.service.RewardService
import com.reward.common.Response
import com.reward.core.dto.RewardViewResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val GET_REWARD_REQUEST = "/v1/api/rewards"

/**
 * 보상 데이터 조회 API
 */
@RestController
@RequestMapping(GET_REWARD_REQUEST)
class RewardFindApiController(
    private val rewardService: RewardService
) {

    @GetMapping("{rewardId}")
    fun getReward(
        @PathVariable rewardId: Long
    ): ResponseEntity<Response<RewardViewResponse>> {
        val content = rewardService.getReward(rewardId)

        return ResponseEntity(Response.of(content), HttpStatus.OK)
    }

}