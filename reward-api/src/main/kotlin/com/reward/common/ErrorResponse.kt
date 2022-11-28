package com.reward.common

import com.fasterxml.jackson.annotation.JsonFormat
import com.reward.core.common.ErrorCode
import org.springframework.validation.BindingResult
import java.time.LocalDateTime

data class ErrorResponse private constructor(
    val message: String,
    val status: Int,
    val errors: List<FieldError>,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val datetime: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun of(code: ErrorCode) = ErrorResponse(
            message = code.message,
            status = code.status,
            errors = listOf(),
        )

        fun of(bindingResult: BindingResult) = ErrorResponse(
            message = "INVALID DATA",
            status = 400,
            errors = FieldError.of(bindingResult)
        )
    }

    data class FieldError private constructor(
        val field: String,
        val value: String,
        val cause: String,
    ) {
        companion object {
            fun of(bindingResult: BindingResult): List<FieldError> =
                bindingResult.fieldErrors
                    .map {
                        FieldError(
                            field = it.field,
                            value = it.rejectedValue.toString(),
                            cause = it.defaultMessage ?: ""
                        )
                    }.toList()
        }

    }
}