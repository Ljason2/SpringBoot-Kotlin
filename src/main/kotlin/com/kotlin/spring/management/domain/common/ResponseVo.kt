package com.kotlin.spring.management.domain.common

data class ResponseVo(
    val status: String?,
    val message: String?,
    val errorDetail: String?,
    val data: Any?,
    val timestamp: Long = System.currentTimeMillis()
)