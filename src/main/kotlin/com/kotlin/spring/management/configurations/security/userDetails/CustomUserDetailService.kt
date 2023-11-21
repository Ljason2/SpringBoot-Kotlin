package com.kotlin.spring.management.configurations.security.userDetails

import com.kotlin.spring.management.configurations.security.userDetails.CustomUserDetails
import com.kotlin.spring.management.dto.user.UserDTO
import com.kotlin.spring.management.services.user.UserBasicService
import io.jsonwebtoken.Claims
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(private val userBasicService: UserBasicService) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val userObject = getUserObjectById(username)
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

    fun loadUserByUsername(claims: Claims): UserDetails {
        return ApiUserDetails(
            id = claims.id,
            company = claims["company"].toString()
        )
    }

    fun getUserObjectById(id: String): UserDTO {
        return userBasicService.getUserById(id).extractData()
    }

}

