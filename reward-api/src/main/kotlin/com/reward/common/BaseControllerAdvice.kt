package com.reward.common

import com.reward.core.common.RewardServiceRuntimeException
import order.api.common.CommonErrorCode.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class BaseControllerAdvice {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(
        e: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.error("Exception {}", request.requestURI, e)

        val errorResponse = ErrorResponse.of(INTERNAL_SERVER_ERROR)

        return ResponseEntity(errorResponse, HttpStatus.valueOf(errorResponse.status))
    }

    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(e: RuntimeException, req: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error("RuntimeException {}", req.requestURI, e)

        val errorResponse = if (e is RewardServiceRuntimeException) {
            ErrorResponse.of(e.code)
        } else {
            ErrorResponse.of(BAD_REQUEST)
        }

        return ResponseEntity(errorResponse, HttpStatus.valueOf(errorResponse.status))
    }

    @ExceptionHandler(value = [BindException::class, MethodArgumentNotValidException::class])
    fun methodArgumentNotValidExceptionHandler(
        e: BindException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.error("MethodArgumentNotValidException {}", request.requestURI, e)

        val errorResponse = ErrorResponse.of(e.bindingResult)

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}