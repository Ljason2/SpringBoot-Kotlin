package com.kotlin.spring.management.controllers.user

import com.kotlin.spring.management.domain.common.ResponseVo
import com.kotlin.spring.management.dto.user.UserRegistrationForm
import com.kotlin.spring.management.services.user.UserBasicService
import com.kotlin.spring.management.services.user.UserRegistrationService
import com.kotlin.spring.management.utils.ProcessingUtil.ProcessingUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/user/register")
class UserRegistrationController(
    private val userBasicService: UserBasicService,
    private val userRegistrationService: UserRegistrationService
) {


    @GetMapping("/")
    fun registrationPage(): String {
        return "signIn"
    }

    @PostMapping("/register")
    fun registerUserAction(
        @RequestBody(required = true) registrationForm: UserRegistrationForm,
        request: HttpServletRequest,
        response: HttpServletResponse,
        redirectAttr: RedirectAttributes
    ) {
        val processingUtil = ProcessingUtil("User Register Process")
        userRegistrationService.registerNewUser(processingUtil, registrationForm)
    }

    @GetMapping("/exists/{id}")
    @ResponseBody
    fun isIdDuplicated(
        @PathVariable(value = "id") id: String,
    ): ResponseEntity<ResponseVo> {
        val response = userBasicService.isUserExistsInDatabase(id).adaptForApi()
        return ResponseEntity(response, HttpStatus.OK)
    }

}