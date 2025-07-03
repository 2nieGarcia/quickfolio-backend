package com.joybreadstudios.quickfolio.dto

// Using 'val' makes the properties read-only, which is good practice for request objects.
data class SignUpDTO(
    val username: String,
    val email: String,
    val password: String
)