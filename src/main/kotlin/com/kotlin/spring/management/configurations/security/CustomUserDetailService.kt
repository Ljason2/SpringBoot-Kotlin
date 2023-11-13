package com.kotlin.spring.management.configurations.security

import com.kotlin.spring.management.services.user.UserBasicService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CustomUserDetailService(private val userBasicService: UserBasicService) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        userBasicService.getUserById(username).let { userObject ->
            if (userObject == null) throw UsernameNotFoundException("There is No Such User")
            return CustomUserDetails(
                id = userObject.id,
                name = userObject.name,
                company = userObject.company,
                position = userObject.position,
                phone = userObject.phone,
                inserted = userObject.inserted,
                lastLogin = null,
                credentials = userBasicService.getUserCredentialsById(username),
                authorities = userObject.roles
            )
        }
    }

}