package com.kotlin.spring.management.configurations.security.loginLogs

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper

@Mapper
interface LoginLogMapper {
    @Insert("INSERT INTO user_login_logs (id, failureId, ip, device, status, company) VALUES(#{id}, #{failureId}, #{ip}, #{device}, #{status}, #{company})")
    fun insertLogInRecord(logInLog: LogInLogDTO): Int
}