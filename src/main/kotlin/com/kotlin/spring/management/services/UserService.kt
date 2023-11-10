package com.kotlin.spring.management.services

import com.kotlin.spring.management.dto.UserDTO
import com.kotlin.spring.management.repositories.mappers.TestMapper
import org.springframework.stereotype.Service

@Service
class UserService(private val testMapper: TestMapper) {

    fun isUserExistsInDatabase(id: String) : Boolean {
        return testMapper.countUserId(id) > 0
    }

    fun getUserDataById(id: String): UserDTO {
        return testMapper.selectUserInfoById(id)
    }

    fun getUserAuthListById(id: String): List<String>{
        return testMapper.selectUserAuthList(id)
    }
}