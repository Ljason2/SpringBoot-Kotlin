package com.kotlin.spring.management.configurations.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

data class CustomUserDetails(
    var id: String,
    var name: String,
    var company: String?,
    var position: String?,
    var phone: String,
    var inserted: LocalDateTime,
    var lastLogin: LocalDateTime?,
    var credentials: String,
    var authorities: List<String>
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.authorities as MutableCollection<out GrantedAuthority>
    }

    override fun getPassword(): String {
        return this.credentials
    }

    override fun getUsername(): String {
        return this.id
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}