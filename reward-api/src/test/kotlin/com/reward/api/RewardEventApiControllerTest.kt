package com.reward.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.reward.core.dto.RewardCreateRequest
import com.reward.core.dto.RewardPublishRequest
import com.reward.core.exception.RewardErrorCode
import com.reward.core.service.RewardService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.LocalDateTime

@AutoConfigureMockMvc
@SpringBootTest
class RewardEventApiControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var rewardService: RewardService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `존재하지 않는 보상금 요청 시 404NotFound`() {
        val request = RewardPublishRequest(memberId = 2, rewardId = 999)
        val body = objectMapper.writeValueAsString(request)

        mockMvc.post("$REWARD_EVENT_REQUEST_URL") {
            contentType = MediaType.APPLICATION_JSON
            content = body
        }
            .andExpect {
                status { isNotFound() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$['message']") { value(RewardErrorCode.REWARD_NOT_FOUND.message) }
                    jsonPath("$['status']") { value(RewardErrorCode.REWARD_NOT_FOUND.status) }
                }

            }
    }

    @Test
    fun `보상금 지급 요청 시 200OK`() {
        val rewardRequest = RewardCreateRequest(
            title = "test-title", description = "test-desc", rewardAmount = 1000,
            count = 10, startDateTime = LocalDateTime.now(), endDateTime = LocalDateTime.now()
        )
        val reward = rewardService.createReward(rewardRequest)

        val request = RewardPublishRequest(memberId = 1, rewardId = reward.id)
        val body = objectMapper.writeValueAsString(request)

        mockMvc.post("$REWARD_EVENT_REQUEST_URL") {
            contentType = MediaType.APPLICATION_JSON
            content = body
        }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$['content'].memberId") { value(request.memberId) }
                    jsonPath("$['content'].amount") { value(rewardRequest.rewardAmount) }
                    jsonPath("$['content'].winningCount") { value(1) }
                }
            }
    }
}