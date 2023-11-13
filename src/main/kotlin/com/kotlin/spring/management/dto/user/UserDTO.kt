package com.kotlin.spring.management.dto.user

import java.time.LocalDateTime


data class UserDTO(
    var id: String = "",
    var name: String = "",
    var company: String?,
    var position: String?,
    var phone: String = "",
    var email: String = "",
    var inserted: LocalDateTime,
    var certification: Boolean,
    var roles: List<String> = listOf("")
)