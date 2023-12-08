package com.kotlin.spring.management.configurations.security

import com.kotlin.spring.management.configurations.filters.CustomLoginRedirectionFilter
import com.kotlin.spring.management.configurations.filters.JsonWebTokenFilter
import com.kotlin.spring.management.configurations.security.jwt.JwtProvider
import com.kotlin.spring.management.configurations.security.userDetails.CustomUserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val customUserDetailsService: CustomUserDetailService,
    private val customAuthenticationSuccessHandler: CustomAuthenticationSuccessHandler,
    private val customAuthenticationFailureHandler: CustomAuthenticationFailureHandler,
    private val jwtProvider: JwtProvider
) {

    @Autowired
    fun configureAuthenticationManagerBuilder(authenticationManagerBuilder: AuthenticationManagerBuilder, passwordEncoder: PasswordEncoder) {
        authenticationManagerBuilder
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder)
    }

    @Bean
    open fun configureSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf {
                disable()
            }
            authorizeHttpRequests {
                authorize("/css/**", permitAll)
                authorize("/js/**", permitAll)
                authorize("/assets/**", permitAll)

                authorize("/login", permitAll)
                authorize("/api/login", permitAll)
                authorize("/user/register/**", permitAll)
                authorize("/swagger-ui.html", permitAll)
                authorize("/swagger-ui/**", permitAll)

                authorize("/**", authenticated)
            }
            formLogin {
                loginPage = "/login"
                authenticationSuccessHandler = customAuthenticationSuccessHandler
                authenticationFailureHandler = customAuthenticationFailureHandler
            }
            logout {
                logoutUrl = "/logout"
                logoutRequestMatcher = AntPathRequestMatcher("/logout", "GET")
                logoutSuccessUrl = "/login"
                invalidateHttpSession = true
                deleteCookies("JSESSIONID")
            }
            httpBasic {

            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(CustomLoginRedirectionFilter())
            addFilterBefore<UsernamePasswordAuthenticationFilter>(JsonWebTokenFilter(jwtProvider))
        }

        return http.build()
    }


}
