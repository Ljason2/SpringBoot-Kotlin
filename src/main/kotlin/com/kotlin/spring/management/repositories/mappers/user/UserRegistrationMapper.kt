package com.kotlin.spring.management.repositories.mappers.user

import com.kotlin.spring.management.dto.user.UserRegistrationForm
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserRegistrationMapper {

    @Insert(" INSERT INTO users (id, name, company, position, phone, email) VALUES(#{id}, #{name}, #{company}, #{position}, #{phone}, #{email})")
    fun insertNewUser(registrationForm: UserRegistrationForm): Int

    @Insert(" INSERT INTO user_credentials (id, password) VALUES(#{id}, #{encodedPassword})")
    fun insertNewUserCredentials(id: String, encodedPassword: String)

    @Insert(" INSERT INTO user_roles (id, role) VALUES(#{id}, 'ROLE_GUEST')")
    fun insertNewUserRoleGuest(id: String)

}