package com.joybreadstudios.quickfolio.controller

import com.joybreadstudios.quickfolio.dto.SignUpDTO
import com.joybreadstudios.quickfolio.dto.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
class TestController {

    @GetMapping
    fun test(): ResponseEntity<ApiResponse<String>> {
        return try {
            ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse(success = true, data = "Test successfully!")
            )
        } catch (e: Exception) { // TODO auth exception
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ApiResponse(success = false, data = "Authentication token invalid, please re-login.")
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