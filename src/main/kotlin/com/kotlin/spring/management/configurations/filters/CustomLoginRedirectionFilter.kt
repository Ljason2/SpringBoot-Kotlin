package com.kotlin.spring.management.configurations.filters

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.logging.LogFactory
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

class CustomLoginRedirectionFilter : Filter {
    private val logger = LogFactory.getLog(CustomLoginRedirectionFilter::class.java)

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication

/*        logger.info("Request URI is: ${httpRequest.requestURI}")
        if (authentication != null) {
            logger.info("Authentication class: ${authentication.javaClass}")
            logger.info("Is authenticated: ${authentication.isAuthenticated}")
        } else {
            logger.info("Authentication is null")
        }*/

        if ("/login" == httpRequest.requestURI) {
            if (authentication != null && authentication.isAuthenticated && authentication !is AnonymousAuthenticationToken) {
                logger.info("Redirecting to / because the user is already authenticated.")
                httpResponse.sendRedirect("/")
                return
            }
        }

        chain.doFilter(request, response)
    }
}