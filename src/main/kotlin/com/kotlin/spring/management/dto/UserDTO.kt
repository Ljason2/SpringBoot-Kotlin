package com.kotlin.spring.management.dto


data class UserDTO(
    var id: String = "",
    var password: String = "",
    var authorities: List<String> = listOf("")
)