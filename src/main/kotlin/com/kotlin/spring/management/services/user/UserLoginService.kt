package com.kotlin.spring.management.services.user

import com.kotlin.spring.management.configurations.security.jwt.JwtProvider
import com.kotlin.spring.management.domains.common.ServiceResponse
import org.springframework.stereotype.Service

@Service
class UserLoginService(
    private val userBasicService: UserBasicService,
    private val jwtProvider: JwtProvider
) {


    fun apiLoginService(
        id: String,
        rawPassword: String
    ): ServiceResponse<String> {
        return if (userBasicService.authenticate(id, rawPassword).extractStatus()){
            ServiceResponse.generateData(
                "JWT Generation",
                { jwtProvider.generateToken(id) },
                "Login Successful, JWT generated without String Bearer"
            )
        } else {
            ServiceResponse.simpleError("JWT Generation")
        }
    }

}