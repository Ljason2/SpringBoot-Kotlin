package com.kotlin.spring.management

import com.kotlin.spring.management.repositories.mappers.TestMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class RegisterUserSample() {

    @Autowired
    private lateinit var testMapper: TestMapper
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    @Test
    fun testRegisterUser() {
        val password = passwordEncoder.encode("password")
        testMapper.insertUser("test2", password, "name")
    }

}
