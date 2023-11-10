package com.kotlin.spring.management.repositories.mappers

import com.kotlin.spring.management.dto.UserDTO
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface TestMapper {
    @Select(" SELECT text FROM test WHERE text = 'HelloWorld' ")
    fun selectStringHelloWorld() : String

    @Insert("INSERT INTO users (id, password, name) VALUES (#{id}, #{password}, #{name})")
    fun insertUser(id: String, password: String, name: String)

    @Select(" SELECT COUNT(id) FROM users WHERE id = #{id}")
    fun countUserId(id: String): Int

    @Select(" SELECT id, password, name FROM users WHERE id = #{id}")
    fun selectUserInfoById(id: String): UserDTO

    @Select(" SELECT authority FROM authorities WHERE id = #{id}")
    fun selectUserAuthList(id: String): List<String>

}