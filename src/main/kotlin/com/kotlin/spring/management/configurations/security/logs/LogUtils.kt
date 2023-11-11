package com.kotlin.spring.management.configurations.security.logs

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class LogUtils(private val logService: LogService) {


}