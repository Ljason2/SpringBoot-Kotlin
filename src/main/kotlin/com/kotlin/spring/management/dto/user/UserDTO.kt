package com.kotlin.spring.management.dto.user

import java.time.LocalDateTime


data class UserDTO(
    var id: String = "",
    var name: String = "",
    var company: String? = null,
    var position: String? = null,
    var phone: String = "",
    var email: String = "",
    var inserted: LocalDateTime = LocalDateTime.now(),
    var certification: Boolean = false,
    var roles: List<String> = listOf("")
)