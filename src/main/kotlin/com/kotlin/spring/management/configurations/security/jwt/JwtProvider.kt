package com.kotlin.spring.management.configurations.security.jwt

import com.kotlin.spring.management.dto.user.UserDTO
import com.kotlin.spring.management.services.user.UserBasicService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(private val userBasicService: UserBasicService) {
    companion object {
        private const val KEY_STRING = "ehdgoanfrhkqorentksdlakfmrhekfgehfhrgksmsladalqhdngktkdnflskfkakstp"
        private const val EXPIRATION_TIME: Long = 1000 * 60 * 60 * 10 // 10 Hours

        private const val ISSUER = "ISSUER - SPRINGBOOT - KOTLIN"
        private const val AUDIENCE = "AUDIENCE - SPRINGBOOT - KOTLIN"

        private val SECRET_KEY: SecretKey = Keys.hmacShaKeyFor(KEY_STRING.toByteArray())
    }

    fun generateToken(id: String): String {
        val userObject: UserDTO = userBasicService.getUserById(id).extractData()
        return Jwts.builder()
            .header()
                .type("JWT")
                .and()
            .claims()
                .id(id)
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
        } catch (e: JwtException) {
            false
        }
    }

    fun isTokenExpired(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .payload
                .expiration
                .before(Date())
        } catch (e: JwtException) {
            false
        }
    }

    fun extractIdFromToken(token: String): String {
        return Jwts.parser()
            .verifyWith(SECRET_KEY)
            .build()
            .parseSignedClaims(token)
            .payload
            .id
    }

    fun extractClaimsFromToken(token: String): Claims {
        return Jwts.parser()
            .verifyWith(SECRET_KEY)
            .build()
            .parseSignedClaims(token)
            .payload
    }



}