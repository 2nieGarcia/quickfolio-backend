package com.joybreadstudios.quickfolio.utils

import com.joybreadstudios.quickfolio.configs.JwtConfig
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtils (private val jwtConfig: JwtConfig){
    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtConfig.expiration))
            .signWith(Keys.hmacShaKeyFor(jwtConfig.secret.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractUsername(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtConfig.secret.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validateToken(token: String): Boolean {
        return try {
            extractUsername(token)
            true
        } catch (e: Exception) {
            false
        }
    }
}