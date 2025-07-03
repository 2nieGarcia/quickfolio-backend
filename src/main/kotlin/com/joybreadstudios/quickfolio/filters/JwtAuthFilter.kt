package com.joybreadstudios.quickfolio.filters

import com.joybreadstudios.quickfolio.repository.UserRepository
import com.joybreadstudios.quickfolio.utils.JwtUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        val token = authHeader?.takeIf { it.startsWith("Bearer ") }?.substring(7)

        if (token != null) {
            val username = jwtUtils.extractUsername(token)
            if (SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userRepository.findByUsername(username)
                if (jwtUtils.validateToken(token)) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        userDetails, null
                    ).apply {
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    }
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}
