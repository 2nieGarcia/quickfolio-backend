package com.joybreadstudios.quickfolio.domain

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "users") // This annotation is optional but good practice
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Automatically generate a unique ID
    val id: UUID? = null,

    @Column(nullable = false, unique = true) // Database constraints
    var username: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(name = "password_hash", nullable = false) // Store the hashed password, not plain text
    var passwordHash: String
)