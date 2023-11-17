package com.kotlin.spring.management.configurations.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val customUserDetailsService: CustomUserDetailService,
    private val customAuthenticationSuccessHandler: CustomAuthenticationSuccessHandler,
    private val customAuthenticationFailureHandler: CustomAuthenticationFailureHandler
) {

    @Autowired
    fun configureAuthenticationManagerBuilder(auth: AuthenticationManagerBuilder, passwordEncoder: PasswordEncoder) {
        auth
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder)
    }

    @Bean
    open fun configureSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
                authorize("/login", permitAll)
                authorize("/user/register/**", permitAll)
            }
            formLogin {
                loginPage = "/login"
                authenticationSuccessHandler = customAuthenticationSuccessHandler
                authenticationFailureHandler = customAuthenticationFailureHandler
            }
            logout {
                logoutUrl = "/logout"
                logoutSuccessUrl = "/login"
            }
            httpBasic {

            }
        }
        return http.build()
    }


}
