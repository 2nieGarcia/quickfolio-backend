package com.joybreadstudios.quickfolio.controller

import com.joybreadstudios.quickfolio.dto.SignUpDTO
import com.joybreadstudios.quickfolio.dto.request.LoginDTO
import com.joybreadstudios.quickfolio.dto.response.ApiResponse
import com.joybreadstudios.quickfolio.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService // Dependency Injection
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpDTO): ResponseEntity<ApiResponse<String>> {
        return try {
            authService.registerUser(request)
            ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse(success = true, data = "User registered successfully!")
            )
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse(success = false, error = e.message)
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse(success = false, error = "An unexpected error occurred.")
            )
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginDTO): ResponseEntity<ApiResponse<String>> {
        return try {
            val response = authService.loginUser(request)
            ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse(success = true, data = response)
            )
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse(success = false, error = e.message)
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse(success = false, error = "An unexpected error occurred.")
            )
        }
    }
}