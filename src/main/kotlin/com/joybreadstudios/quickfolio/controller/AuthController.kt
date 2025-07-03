package com.joybreadstudios.quickfolio.controller

import com.joybreadstudios.quickfolio.dto.SignUpRequest
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
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Any> {
        return try {
            authService.registerUser(request)
            // On success, return a simple message with a 201 Created status
            ResponseEntity.status(HttpStatus.CREATED).body(mapOf("message" to "User registered successfully!"))
        } catch (e: IllegalArgumentException) {
            // If the service throws an exception (e.g., username taken), return a 409 Conflict status
            ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to e.message))
        } catch (e: Exception) {
            // For any other unexpected errors, return a 500 Internal Server Error
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to "An unexpected error occurred."))
        }
    }
}