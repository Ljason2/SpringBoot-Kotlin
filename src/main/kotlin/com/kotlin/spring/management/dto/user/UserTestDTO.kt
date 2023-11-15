package com.kotlin.spring.management.dto.user

import com.kotlin.spring.management.configurations.annotations.NoArgsConstructor
import java.time.LocalDateTime

@NoArgsConstructor
data class UserTestDTO(
    var id: String?,
    var name: String?,
    var company: String?,
    var position: String?,
    var phone: String?,
    var email: String?,
    var inserted: LocalDateTime?,
    var certification: Boolean?,
)


/**
 * https://github.com/jeffgbutler/mybatis-kotlin-examples
 *
 * https://mybatis.org/mybatis-dynamic-sql/docs/kotlinMyBatis3.html
 *
 * data class UserDTO(
 *     var id: String = "",
 *     var name: String = "",
 *     var company: String? = null,
 *     var position: String? = null,
 *     var phone: String = "",
 *     var email: String = "",
 *     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
 *     var inserted: LocalDateTime? = null,
 *     var certification: Boolean = false,
 *     var roles: List<String> = listOf("")
 * )
 */