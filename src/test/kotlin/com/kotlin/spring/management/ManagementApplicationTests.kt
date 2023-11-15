package com.kotlin.spring.management

import com.kotlin.spring.management.dto.user.UserRegistrationForm
import com.kotlin.spring.management.services.user.UserRegistrationService
import com.kotlin.spring.management.utils.ProcessingUtil.ProcessingUtil
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.RuntimeException

@SpringBootTest
class ManagementApplicationTests() {


    @Autowired
    private lateinit var userRegistrationService: UserRegistrationService

    @Test
    fun contextLoads() {

        val registrationForm: UserRegistrationForm = UserRegistrationForm(
            id = "123",
            password = "1414",
            passwordCheck = "14141",
            name = "TEST",
            company = "no",
            position = "no",
            phone = "no",
            email = "no"
        )

        val processingUtil = ProcessingUtil("User Register Process")
        userRegistrationService.registerNewUser(processingUtil, registrationForm)
    }

}
