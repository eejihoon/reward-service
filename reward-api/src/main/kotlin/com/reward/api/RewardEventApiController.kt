package com.reward.api

import com.reward.common.Response
import com.reward.core.dto.RewardPublishRequest
import com.reward.core.dto.RewardPublishResponse
import com.reward.core.service.RewardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

const val REWARD_EVENT_REQUEST_URL = "/api/v1/rewards/publish"

@RestController
@RequestMapping(REWARD_EVENT_REQUEST_URL)
class RewardEventApiController(
    private val rewardService: RewardService
) {

    @PostMapping
    fun publishReward(
        @RequestBody @Valid request: RewardPublishRequest
    ): ResponseEntity<Response<RewardPublishResponse>> {
        val content = rewardService.publish(request)

        return ResponseEntity(Response.of(content), HttpStatus.OK)
    }


}