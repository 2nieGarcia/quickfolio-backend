package com.joybreadstudios.quickfolio.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)

    val id: UUID? = null,

    var username: String,
    var email: String,
    var passwordHash: String
}