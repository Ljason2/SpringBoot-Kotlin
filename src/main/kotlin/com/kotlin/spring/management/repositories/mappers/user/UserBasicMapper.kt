package com.kotlin.spring.management.repositories.mappers.user

import com.kotlin.spring.management.dto.user.UserDTO
import com.kotlin.spring.management.dto.user.UserTestDTO
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserBasicMapper {

    @Select("SELECT password FROM user_credentials WHERE id = #{id}")
    fun selectUserCredentialsById(id: String): String

    @Select(" SELECT COUNT(id) FROM users WHERE id = #{id}")
    fun countUserId(id: String): Int

    @Select(" SELECT role FROM user_roles WHERE id = #{id}")
    fun selectUserRolesById(id: String): List<String>

    @Select(" SELECT id, name, company, position, phone, email, inserted, certification FROM users WHERE id = #{id}")
    fun selectUserById(id: String): UserDTO

    @Select(" SELECT id FROM users WHERE id = #{id}")
    fun selectUserByIdTest(id: String): UserTestDTO

}