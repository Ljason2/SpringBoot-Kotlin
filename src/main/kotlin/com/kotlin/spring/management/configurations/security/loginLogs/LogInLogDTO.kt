package com.kotlin.spring.management.configurations.security.loginLogs

import com.kotlin.spring.management.annotations.NoArgsConstructor
import java.time.LocalDateTime

@NoArgsConstructor
data class LogInLogDTO(
    var record: Int,
    var id: String,
    var failureId: String,
    var timestamp: LocalDateTime,
    var ip: String,
    var device: String,
    var status: String,
    var company: String
)