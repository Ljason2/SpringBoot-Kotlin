package com.kotlin.spring.management.utils.LogUtils

import com.kotlin.spring.management.configurations.security.loginLogs.LoginLogService
import org.springframework.stereotype.Component

@Component
class LogUtils(private val loginLogService: LoginLogService) {


}