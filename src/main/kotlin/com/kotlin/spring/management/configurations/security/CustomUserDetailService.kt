package com.kotlin.spring.management.configurations.security

import com.kotlin.spring.management.services.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(private val userService: UserService) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {

        // Check If The User Exists
        if (!userService.isUserExistsInDatabase(username)) throw UsernameNotFoundException("There is No Such User")

        var userObject = userService.getUserDataById(username)
        userObject.authorities = userService.getUserAuthListById(username)

        return CustomUserDetails(
            id = userObject.id,
            password = userObject.password,
            authorities = userObject.authorities
        )
    }

}