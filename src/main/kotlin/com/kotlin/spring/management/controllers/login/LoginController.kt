package com.kotlin.spring.management.controllers.login

import com.kotlin.spring.management.domains.common.apiResponse.ResponseVo
import com.kotlin.spring.management.domains.common.login.LoginRequest
import com.kotlin.spring.management.services.user.UserLoginService
import com.kotlin.spring.management.utils.ResponseEntityGenerator.ResponseEntityGenerator
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody

@Tag(name = "LoginController", description = "LoginController")
@Controller
class LoginController(private val userLoginService: UserLoginService) {
    private val logger = LoggerFactory.getLogger(LoginController::class.java)

    @GetMapping("/login")
    fun loginPage(): String {
        return "loginPage"
    }

    @Operation(summary = "API 로그인", description = "ID 와 PASSWORD 를 입력하여 로그인을 요청합니다. JWT 를 리턴받습니다.")
    @PostMapping(path = ["/api/login"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun apiLoginRequest(
        @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<ResponseVo> {
        logger.info(loginRequest.toString())
        val response: ResponseVo = userLoginService.apiLoginService(loginRequest.id, loginRequest.password).adaptForApi()
        return ResponseEntityGenerator.generateResponse(response)
    }


}