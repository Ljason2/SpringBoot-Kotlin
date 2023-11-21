package com.kotlin.spring.management.configurations.security

import com.kotlin.spring.management.configurations.security.userDetails.CustomUserDetails
import com.kotlin.spring.management.utils.LogUtils.LogUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationSuccessHandler(
    private val logUtils: LogUtils
): AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        logUtils.recordLoginSuccessLog(request, authentication.principal as CustomUserDetails)
        response.sendRedirect(request.contextPath + "/")
    }

}