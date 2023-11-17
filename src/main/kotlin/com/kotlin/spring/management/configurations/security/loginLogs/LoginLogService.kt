package com.kotlin.spring.management.configurations.security.loginLogs

import com.kotlin.spring.management.domains.common.ServiceResponse
import org.springframework.stereotype.Service

@Service
class LoginLogService(private val loginLogMapper: LoginLogMapper) {

    fun recordLoginLogs(logInLog: LogInLogDTO): ServiceResponse<Boolean>{
        return ServiceResponse.simpleStatus(
            "LoginLog-Service - RecordLoginLogs",
            {
                loginLogMapper.insertLogInRecord(logInLog) == 1
            },
            "로그인 로그가 성공적으로 기록되었습니다.",
            "로그인 로그 기록 중 오류가 발생하였습니다."
        )
    }

}