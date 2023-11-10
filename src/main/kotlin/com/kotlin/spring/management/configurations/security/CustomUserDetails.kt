package com.kotlin.spring.management.configurations.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserDetails(
    var id: String?,
    var password: String?,
    var authorities: List<String>?
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.authorities as MutableCollection<out GrantedAuthority>
    }

    override fun getPassword(): String {
        return this.password.toString()
    }

    override fun getUsername(): String {
        return this.id.toString()
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