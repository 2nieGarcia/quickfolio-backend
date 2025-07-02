package com.joybreadstudios.quickfolio.repository

import com.joybreadstudios.quickfolio.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    // Spring Data JPA automatically creates the implementation for these methods based on their names.
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
}