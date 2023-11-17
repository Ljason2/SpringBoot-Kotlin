package com.kotlin.spring.management.services.user

import com.kotlin.spring.management.domains.common.ServiceResponse
import com.kotlin.spring.management.dto.user.UserDTO
import com.kotlin.spring.management.repositories.mappers.user.UserBasicMapper
import org.springframework.stereotype.Service

@Service
class UserBasicService(private val userBasicMapper: UserBasicMapper) {

    fun isUserExistsInDatabase(id: String): ServiceResponse<Boolean> {
        return ServiceResponse.simpleStatus(
            "UserBasicService - isUserExistsInDatabase",
            {
                userBasicMapper.countUserId(id) > 0
            },
            "$id 가 회원정보내에 존재합니다.",
            "$id 가 회원정보내에 존재하지 않습니다."
        )
    }

    fun getUserById(id: String): ServiceResponse<UserDTO> {
        return ServiceResponse.generateData(
            "UserBasicService - getUserById",
            {
                userBasicMapper.selectUserById(id)?.apply {
                    this.roles = getUserRolesById(this.id).extractData()
                }
            },
            "유저 정보를 성공적으로 불러 왔습니다.",
            "유저 정보를 불러오는 도중 문제가 발생 하였습니다."
        )
    }

    fun getUserCredentialsById(id: String): ServiceResponse<String> {
        return ServiceResponse.generateData(
            "UserBasicService - getUserCredentialsById",
            {
                userBasicMapper.selectUserCredentialsById(id)
            },
            "유저 비밀번호를 성공적으로 불러 왔습니다.",
            "유저 비밀번호를 불러오는 도중 문제가 발생 하였습니다."
        )

    }

    fun getUserRolesById(id: String): ServiceResponse<List<String>> {
        return ServiceResponse.generateData(
            "UserBasicService - getUserRolesById",
            {
                userBasicMapper.selectUserRolesById(id)
            },
            "$id 유저의 권한 목록을 성공적으로 불러 왔습니다.",
            "$id 유저의 권한 목록을 불러오는데 실패 하였습니다."
        )
    }
}