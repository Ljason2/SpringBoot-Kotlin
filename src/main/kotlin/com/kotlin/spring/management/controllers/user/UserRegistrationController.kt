package com.kotlin.spring.management.controllers.user

import com.kotlin.spring.management.dto.user.UserRegistrationForm
import com.kotlin.spring.management.services.user.UserBasicService
import com.kotlin.spring.management.services.user.UserRegistrationService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/user/register")
class UserRegistrationController(
    private val userBasicService: UserBasicService,
    private val userRegistrationService: UserRegistrationService
) {


    @PostMapping("/register")
    fun registerUserAction(
        @RequestBody(required = true) registrationForm: UserRegistrationForm,
        request: HttpServletRequest,
        response: HttpServletResponse,
        redirectAttr: RedirectAttributes
    ) {

    }

    @GetMapping("/exists/{id}")
    fun isIdDuplicated(
        @PathVariable(value = "id") id: String
    ) {

    }

}