package com.joybreadstudios.quickfolio.service

import com.joybreadstudios.quickfolio.domain.User
import com.joybreadstudios.quickfolio.dto.SignUpDTO
import com.joybreadstudios.quickfolio.dto.request.LoginDTO
import com.joybreadstudios.quickfolio.repository.UserRepository
import com.joybreadstudios.quickfolio.utils.JwtUtils
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    // Dependencies are injected by Spring's constructor injection
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils
) {

    fun registerUser(request: SignUpDTO) {
        // 1. Check if username or email already exists to prevent duplicates
        if (userRepository.findByUsername(request.username) != null) {
            throw IllegalArgumentException("Username '${request.username}' is already taken.")
        }
        if (userRepository.findByEmail(request.email) != null) {
            throw IllegalArgumentException("Email '${request.email}' is already registered.")
        }

        // 2. Create a new User entity instance
        val newUser = User(
            username = request.username,
            email = request.email,
            // 3. Hash the plain-text password before saving it
            passwordHash = passwordEncoder.encode(request.password)
        )

        // 4. Save the new user to the database
        userRepository.save(newUser)

        println("Successfully registered user: ${newUser.username}")
    }

    fun loginUser(request: LoginDTO): String {
        val user = userRepository.findByEmail(request.email);
        if (user == null) {
            throw IllegalArgumentException("User is not registered in the website yet.")
        }
        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw IllegalArgumentException("You have entered the wrong password.")
        }
        return jwtUtils.generateToken(user.username);
    }
}