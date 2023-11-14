package com.kotlin.spring.management.services.user

import com.kotlin.spring.management.dto.user.UserRegistrationForm
import com.kotlin.spring.management.repositories.mappers.user.UserRegistrationMapper
import com.kotlin.spring.management.utils.ProcessingUtil.ProcessingUtil
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 *     UserRegistrationForm
 *     @Schema(description = "아이디")
 *     var id: String,
 *     @Schema(description = "평문 비밀번호")
 *     var password : String,
 *     @Schema(description = "평문 비밀번호 확인")
 *     var passwordCheck: String,
 *     @Schema(description = "이름")
 *     var name: String,
 *     @Schema(description = "회사")
 *     var company: String?,
 *     @Schema(description = "직책")
 *     var position: String?,
 *     @Schema(description = "전화번호")
 *     var phone: String,
 *     @Schema(description = "이메일")
 *     var email: String
 */

@Service
class UserRegistrationService(
    val userBasicService: UserBasicService,
    val userRegistrationMapper: UserRegistrationMapper
) {

    // Regex List
    companion object {
        // 아이디 :
        private val REGEX_ID = Regex("^[a-z][a-z0-9]{3,19}$")
        // 비밀번호 : 최소 10자리 이상의 영어 대문자, 소문자, 숫자, 특수문자 중 2종류 조합
        private val REGEX_PASSWORD = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{10,}|(?=.*[A-Za-z])(?=.*[^A-Za-z\\d])[A-Za-z\\W]{10,}|(?=.*\\d)(?=.*[^A-Za-z\\d])\\d[\\W\\d]{9,}|(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\W]{10,}$")
        // 이름 : 최소 1글자 이상의 한글문자
        private val REGEX_NAME = Regex("""[\uAC00-\uD7A3]+""")
        // 전화번호 : 02로 시작시 8글자이상, 그 이외의 경우 9글자 이상 의 숫자
        private val REGEX_PHONE_NUMBER = Regex("""^(02\d{6,}|0\d{8,})$""")
        // 이메일 : example@example.com 과 같은 이메일 형식
        private val REGEX_EMAIL = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    }

    fun registerNewUser(
        processingUtil: ProcessingUtil,
        registrationForm: UserRegistrationForm
    ): Boolean {
        when (validateUserRegistrationForm(processingUtil, registrationForm)) {
            true -> {
                processingUtil.addFunction(
                    "DB 등록",
                    processFunction = {
                        userRegistrationMapper.insertNewUser(registrationForm) == 1
                    },
                    optional = false
                )

            }
            false -> false
        }

        return false
    }

    fun validateUserRegistrationForm(
        processingUtil: ProcessingUtil,
        registrationForm: UserRegistrationForm
    ): Boolean {

        // ID Duplication Check
        processingUtil.addFunction(
            "아이디 중복 체크",
            processFunction = {
                userBasicService.isUserExistsInDatabase(registrationForm.id)
            },
            false
        ).let { process->
            if (!process) {

            }
        }

        // ID Validation Check
        processingUtil.addFunction(
            "아이디 유효성 체크",
            processFunction = {
                registrationForm.id.matches(REGEX_ID)
            },
            false
        )

        // Password & Password Check Equals
        processingUtil.addFunction(
            "비밀번호 값, 비밀번호 확인 값 일치",
            processFunction = {
                registrationForm.password == registrationForm.passwordCheck
            },
            false
        )

        // Password Validation Check
        processingUtil.addFunction(
            "비밀번호 정규식 만족",
            processFunction = {
                registrationForm.password.matches(REGEX_PASSWORD)
            },
            false
        )

        // Name Validation Check
        processingUtil.addFunction(
            "이름 정규식 만족",
            processFunction = {
                registrationForm.name.matches(REGEX_NAME)
            },
            false
        )

        // Company
        processingUtil.addFunction(
            "회사 설정 추후 적용",
            processFunction = {
                false
            },
            true
        )

        // Position
        processingUtil.addFunction(
            "직책 설정 추후 적용",
            processFunction = {
                false
            },
            true
        )

        // Phone
        processingUtil.addFunction(
            "전화번호 정규식 만족",
            processFunction = {
                registrationForm.email.matches(REGEX_PHONE_NUMBER)
            },
            false
        )

        // E-Mail Validation Check
        processingUtil.addFunction(
            "이메일 정규식 만족",
            processFunction = {
                registrationForm.email.matches(REGEX_EMAIL)
            },
            false
        )

        return processingUtil.compile(false)
    }



}