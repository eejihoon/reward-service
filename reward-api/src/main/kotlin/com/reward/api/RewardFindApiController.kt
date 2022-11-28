package com.reward.api

import com.reward.core.service.RewardService
import com.reward.common.Response
import com.reward.core.dto.RewardViewResponse
import com.reward.core.dto.RewardWinnersRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val GET_REWARD_REQUEST = "/v1/api/rewards"


@RestController
@RequestMapping(GET_REWARD_REQUEST)
class RewardFindApiController(
    private val rewardService: RewardService
) {

    /**
     * 보상 데이터 조회 API
     */
    @GetMapping("{rewardId}")
    fun getReward(
        @PathVariable rewardId: Long
    ): ResponseEntity<Response<RewardViewResponse>> {
        val content = rewardService.getReward(rewardId)

        return ResponseEntity(Response.of(content), HttpStatus.OK)
    }

    /**
     *   보상 조회 API: 지정한 일자별 보상 받은 사용자 10명 조회ㅡ정렬 조건 포함
     */
    @GetMapping
    fun getRewardWinners(
        @ModelAttribute request: RewardWinnersRequest
    ) {
        rewardService.getRewardWinners(request)
    }
}