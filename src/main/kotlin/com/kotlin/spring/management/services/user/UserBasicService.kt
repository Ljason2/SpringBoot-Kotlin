package com.kotlin.spring.management.services.user

import com.kotlin.spring.management.dto.user.UserDTO
import com.kotlin.spring.management.repositories.mappers.user.UserBasicMapper
import org.springframework.stereotype.Service

@Service
class UserBasicService(private val userBasicMapper: UserBasicMapper) {

    fun isUserExistsInDatabase(id: String): Boolean {
        return userBasicMapper.countUserId(id) > 0
    }

    fun getUserById(id: String): UserDTO {
        return userBasicMapper.selectUserById(id).apply {
            roles = getUserRolesById(this.id)
        }
    }

    fun getUserCredentialsById(id: String): String{
        return userBasicMapper.selectUserCredentialsById(id)
    }

    fun getUserRolesById(id: String): List<String>{
        return userBasicMapper.selectUserRolesById(id)
    }
}