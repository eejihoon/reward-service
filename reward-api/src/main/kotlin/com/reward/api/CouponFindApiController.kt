package com.reward.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api/coupons")
class CouponFindApiController {

    @GetMapping("/{memberId}")
    fun getCoupons(@PathVariable memberId: String) {
        //
    }

}