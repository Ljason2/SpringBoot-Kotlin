package com.kotlin.spring.management.utils.LogUtils

import com.kotlin.spring.management.configurations.security.userDetails.CustomUserDetails
import com.kotlin.spring.management.configurations.security.loginLogs.LogInLogDTO
import com.kotlin.spring.management.configurations.security.loginLogs.LoginLogService
import com.kotlin.spring.management.dto.user.UserDTO
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class LogUtils(
    private val loginLogService: LoginLogService
) {

    fun recordLoginSuccessLog(request: HttpServletRequest, userDetails: CustomUserDetails) {
        var loginSuccessInfo = LogInLogDTO(
            record = null,
            id = userDetails.id,
            failureId = null,
            timestamp = null,
            ip = this.extractIpInfoFromRequest(request),
            device = "[Device : ${this.extractDeviceAndBrowserInfo(request)["deviceInfo"]}] [Browser : ${this.extractDeviceAndBrowserInfo(request)["browserInfo"]}]",
            status = "success",
            company = userDetails.company
        )
        loginLogService.recordLoginLogs(loginSuccessInfo)
    }

    fun recordLoginFailureLog(request: HttpServletRequest, user: UserDTO) {
        var loginFailureInfo = LogInLogDTO(
            record = null,
            id = user.id,
            failureId = null,
            timestamp = null,
            ip = this.extractIpInfoFromRequest(request),
            device = "[Device : ${this.extractDeviceAndBrowserInfo(request)["deviceInfo"]}] [Browser : ${this.extractDeviceAndBrowserInfo(request)["browserInfo"]}]",
            status = "fail",
            company = user.company
        )
        loginLogService.recordLoginLogs(loginFailureInfo)
    }

    fun recordAnonymousLoginAttempt(request: HttpServletRequest, attemptId: String) {
        var anonymousLoginAttemptLog = LogInLogDTO(
            record = null,
            id = null,
            failureId = attemptId,
            timestamp = null,
            ip = this.extractIpInfoFromRequest(request),
            device = "[Device : ${this.extractDeviceAndBrowserInfo(request)["deviceInfo"]}] [Browser : ${this.extractDeviceAndBrowserInfo(request)["browserInfo"]}]",
            status = "error",
            company = null
        )
        loginLogService.recordLoginLogs(anonymousLoginAttemptLog)
    }


    fun extractDeviceAndBrowserInfo(request: HttpServletRequest): Map<String, String> {
        val resultMap: MutableMap<String, String> = HashMap()
        var deviceInfo = "Unknown"
        var browserInfo = "Unknown"

        var userAgent = request.getHeader("User-Agent")
        if (userAgent != null) {
            userAgent = userAgent.lowercase(Locale.getDefault())
            // Extracting Device Info
            deviceInfo = if (userAgent.contains("mobile")) {
                "Mobile Device"
            } else if (userAgent.contains("tablet")) {
                "Tablet Device"
            } else {
                "Desktop Device"
            }
            // Extracting Browser Info
            browserInfo = if (userAgent.contains("edg")) {
                "Edge Browser"
            } else if (userAgent.contains("chrome")) {
                "Chrome Browser"
            } else if (userAgent.contains("safari")) {
                "Safari Browser"
            } else if (userAgent.contains("firefox")) {
                "FireFox Browser"
            } else if (userAgent.contains("msie") || userAgent.contains("trident")) {
                "Internet Explorer Browser"
            } else {
                "Other Browser"
            }
        }
        resultMap["deviceInfo"] = deviceInfo
        resultMap["browserInfo"] = browserInfo
        return resultMap
    }

    fun extractIpInfoFromRequest(request: HttpServletRequest): String {
        //Extracting IP
        var ip = request.getHeader("X-Forwarded-For")
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_CLIENT_IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
        }
        return ip
    }







}