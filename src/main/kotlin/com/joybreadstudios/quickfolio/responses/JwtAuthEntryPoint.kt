package com.joybreadstudios.quickfolio.responses

import com.fasterxml.jackson.databind.ObjectMapper
import com.joybreadstudios.quickfolio.dto.response.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        val errorResponse = ApiResponse<Unit>(
            success = false,
            error = "Authentication token invalid or expired. Please re-login."
        )
        response.writer.write(ObjectMapper().writeValueAsString(errorResponse))
    }
}