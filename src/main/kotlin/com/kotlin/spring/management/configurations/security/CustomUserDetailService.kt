package com.kotlin.spring.management.configurations.security

import com.kotlin.spring.management.dto.user.UserDTO
import com.kotlin.spring.management.services.user.UserBasicService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(private val userBasicService: UserBasicService) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val userObject: UserDTO = userBasicService.getUserById(username).extractData()
        return CustomUserDetails(
            id = userObject.id,
            name = userObject.name,
            company = userObject.company,
            position = userObject.position,
            phone = userObject.phone,
            inserted = userObject.inserted,
            lastLogin = null,
            credentials = userBasicService.getUserCredentialsById(username).extractData(),
            authorities = userObject.roles
        )
    }
}

