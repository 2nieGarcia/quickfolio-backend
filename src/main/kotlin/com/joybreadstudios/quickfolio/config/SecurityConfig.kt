package com.joybreadstudios.quickfolio.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration // Tells Spring this is a configuration class
@EnableWebSecurity // Enables Spring's web security support
class SecurityConfig {

    @Bean // This is our password encoder bean, now inside a dedicated config class
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean // This bean defines the security rules for our API endpoints
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // Disable CSRF protection for our stateless REST API
            .csrf { it.disable() }
            // Define authorization rules
            .authorizeHttpRequests { auth ->
                auth
                    // Allow ANYONE to access the /api/auth/signup endpoint
                    .requestMatchers("/api/auth/**").permitAll()
                    // Require authentication for any other request
                    .anyRequest().authenticated()
            }

        return http.build()
    }
}