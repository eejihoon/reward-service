package com.reward.api

import com.reward.core.dto.RewardCreateRequest
import com.reward.core.exception.RewardErrorCode
import com.reward.core.service.RewardService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class RewardFindApiControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var rewardService: RewardService

    @Test
    fun `존재하지 않는 보상 데이터 조회 404NotFound`() {

        mockMvc.get("$GET_REWARD_REQUEST/999")
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
    fun `보상 데이터 조회 200OK`() {
        val reward = rewardService.createReward(
            RewardCreateRequest(
                title = "test-title", description = "test-desc", rewardAmount = 1000,
                count = 10, startDateTime = LocalDateTime.now(), endDateTime = LocalDateTime.now()
            )
        )
        mockMvc.get("$GET_REWARD_REQUEST/${reward.id}")
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$['content'].id") { value(reward.id.toInt()) }
                    jsonPath("$['content'].title") { value(reward.title) }
                    jsonPath("$['content'].description") { value(reward.description) }
                }

            }
    }


}