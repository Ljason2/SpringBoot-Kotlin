package com.kotlin.spring.management.domain.common

data class ResponseVo(
    val result: String?,
    val message: String?,
    val exceptionMessage: String?,
    val data: Any?
)