package com.joybreadstudios.quickfolio

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.sql.DataSource

@SpringBootTest
class QuickfolioApplicationTests {

    @Autowired
    private lateinit var dataSource: DataSource

    @Test
    fun `test database connection with SELECT 1`() {
        dataSource.connection.use { conn ->
            conn.createStatement().use { stmt ->
                val rs = stmt.executeQuery("SELECT 1")
                assertTrue(rs.next(), "Expected at least one result from SELECT 1")
                val result = rs.getInt(1)
                println("✅ Successfully connected to Supabase. Result: $result")
                assertEquals(1, result)
            }
        }
    }
}
