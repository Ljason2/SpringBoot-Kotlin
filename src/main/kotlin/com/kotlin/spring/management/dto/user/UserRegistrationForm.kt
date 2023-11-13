package com.kotlin.spring.management.dto.user

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원가입 Form")
data class UserRegistrationForm(
    @Schema(description = "아이디")
    var id: String,
    @Schema(description = "평문 비밀번호")
    var password : String,
    @Schema(description = "평문 비밀번호 확인")
    var passwordCheck: String,
    @Schema(description = "이름")
    var name: String,
    @Schema(description = "회사")
    var company: String,
    @Schema(description = "직책")
    var position: String,
    @Schema(description = "전화번호")
    var phone: String,
    @Schema(description = "이메일")
    var email: String
)