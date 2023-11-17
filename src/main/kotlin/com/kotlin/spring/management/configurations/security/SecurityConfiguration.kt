package com.kotlin.spring.management.configurations.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
// Import this
import org.springframework.security.config.annotation.web.invoke

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val customUserDetailsService: CustomUserDetailService
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
