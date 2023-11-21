package com.kotlin.spring.management.configurations.security.jwt

import com.kotlin.spring.management.configurations.security.userDetails.CustomUserDetailService
import com.kotlin.spring.management.dto.user.UserDTO
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Header
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(private val customUserDetailsService: CustomUserDetailService) {

    private val logger = LoggerFactory.getLogger(JwtProvider::class.java)

    companion object {
        private const val KEY_STRING = "ehdgoanfrhkqorentksdlakfmrhekfgehfhrgksmsladalqhdngktkdnflskfkakstp"
        private const val EXPIRATION_TIME: Long = 1000 * 60 * 60 * 10 // 10 Hours

        private const val ISSUER = "ISSUER - SPRINGBOOT - KOTLIN"
        private const val AUDIENCE = "AUDIENCE - SPRINGBOOT - KOTLIN"

        private val SECRET_KEY: SecretKey = Keys.hmacShaKeyFor(KEY_STRING.toByteArray())
    }

    fun generateToken(id: String): String {
        val userObject = customUserDetailsService.getUserObjectById(id)
        return Jwts.builder()
            .header()
                .type("JWT")
                .and()
            .claims()
                .id(id)
                .add("company", userObject.company)
                .add("roles", userObject.roles)
                .issuer(ISSUER)
                .audience()
                    .add(AUDIENCE)
                    .and()
                .issuedAt(Date())
                .expiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .and()
            .signWith(SECRET_KEY)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
            true
        } catch (e: ExpiredJwtException) {
            throw JwtException("Expired Token")
        } catch (e: JwtException) {
            throw JwtException("Jwt Exception")
        } catch (e: Exception) {
            throw Exception()
        }
    }

    @Deprecated(message = "Method No LongerUsed", replaceWith = ReplaceWith("validateToken(token)"))
    fun isTokenExpired(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .payload
                .expiration
                .before(Date())
        } catch (e: ExpiredJwtException) {
            false
        }
    }

    fun extractPayloadsFromToken(token: String): Claims {
        return Jwts.parser()
            .verifyWith(SECRET_KEY)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun extractTokenFromHttpRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7)
        }
        return null
    }

    fun extractAuthentication(token: String): Authentication {
        val claims = this.extractPayloadsFromToken(token)
        val roles: List<String> = claims["roles"] as List<String>
        return UsernamePasswordAuthenticationToken(
            customUserDetailsService.loadUserByUsername(claims),
            "Password Secured",
            roles.map { SimpleGrantedAuthority(it) }.toMutableList()
        )
    }



}