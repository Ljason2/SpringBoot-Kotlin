package com.kotlin.spring.management.configurations.filters

import com.kotlin.spring.management.configurations.security.jwt.JwtProvider
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter


class JsonWebTokenFilter(
    private val jwtProvider: JwtProvider
): OncePerRequestFilter() {
    private val logger = LoggerFactory.getLogger(JsonWebTokenFilter::class.java)
    private val pathMatcher = AntPathMatcher()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (pathMatcher.match("/api/**", request.servletPath) &&
            !pathMatcher.match("/api/login", request.servletPath)) {
            if (!processJwtAuthentication(request, response)) {
                return
            }
        }
        if (!response.isCommitted) {
            filterChain.doFilter(request, response)
        }
    }

    private fun processJwtAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Boolean {
        try {
            logger.info("JwtFilterChain Activated - RequestURI[${request.servletPath}]")
            val token = jwtProvider.extractTokenFromHttpRequest(request)
            if (SecurityContextHolder.getContext().authentication == null) {
                if (token != null && jwtProvider.validateToken(token)) {
                    SecurityContextHolder.getContext().authentication = jwtProvider.extractAuthentication(token)
                    logger.info("Request Successful")
                    return true
                }
            }
        } catch (e: ExpiredJwtException) {
            this.handleTokenExpiredException(response, e)
        } catch (e: JwtException) {
            this.handleJwtException(response, e)
        } catch (e: AuthenticationException) {
            this.handleAuthenticationException(response, e)
        } catch (e: Exception) {
            this.handleMiscellaneousExceptions(response, e)
        }
        return false
    }

    private fun handleJwtException(response: HttpServletResponse, exception: JwtException) {
        logger.info("Request Failed - JwtException")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write("{\"message\": \"Authorization error: Invalid token.\"}")
        response.writer.flush()
    }

    private fun handleAuthenticationException(response: HttpServletResponse, exception: AuthenticationException) {
        logger.info("Request Failed - AuthenticationException")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write("{\"message\": \"Authorization error: Invalid token.\"}")
        response.writer.flush()
    }

    private fun handleTokenExpiredException(response: HttpServletResponse, exception: ExpiredJwtException) {
        logger.info("Request Failed - ExpiredJwtException")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write("{\"message\": \"Authorization error: Expired token.\"}")
        response.writer.flush()
    }

    private fun handleMiscellaneousExceptions(response: HttpServletResponse, exception: Exception) {
        logger.info("Request Failed - MiscellaneousException")
        response.status = HttpServletResponse.SC_BAD_REQUEST
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write("{\"message\": \"Authorization error: Invalid token.\"}")
        response.writer.flush()
    }

}