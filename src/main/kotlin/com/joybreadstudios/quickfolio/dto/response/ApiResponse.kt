package com.joybreadstudios.quickfolio.dto.response

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null
)
