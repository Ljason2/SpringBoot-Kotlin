package com.kotlin.spring.management.configurations.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    private val customUserDetailService: CustomUserDetailService
): AuthenticationProvider {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    override fun authenticate(authentication: Authentication): Authentication {
        val id = authentication.name
        val password = authentication.credentials.toString()

        return customUserDetailService.loadUserByUsername(id).let { userDetails ->
            if (!passwordEncoder.matches(password, userDetails.password)) {
                throw BadCredentialsException("Bad Credentials")
            }

            UsernamePasswordAuthenticationToken(
                userDetails,
                password,
                userDetails.authorities
            )
        }
    }

    override fun supports(authenticationClass: Class<*>): Boolean {
        return authenticationClass == UsernamePasswordAuthenticationToken::class.java
    }
}