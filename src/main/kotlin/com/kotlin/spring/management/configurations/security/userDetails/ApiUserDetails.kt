package com.kotlin.spring.management.configurations.security.userDetails

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class ApiUserDetails(
    var id: String,
    var company: String?
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return throw Exception("ApiUserDetails Class Does Not Contain Authorities")
    }

    override fun getPassword(): String {
        return "Password Secured"
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