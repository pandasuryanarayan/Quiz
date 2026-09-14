package com.example

import com.example.util.AdminSecurity
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun adminPasswordVerification_isCorrect() {
    // Correct password verified via SHA-256
    assertTrue(AdminSecurity.verifyPassword("logoquiz@suryalabs"))

    // Incorrect password fails
    assertFalse(AdminSecurity.verifyPassword("wrongpassword"))
    assertFalse(AdminSecurity.verifyPassword("logoquiz"))
    assertFalse(AdminSecurity.verifyPassword(""))
    assertFalse(AdminSecurity.verifyPassword("Logoquiz@suryalabs"))
  }
}
