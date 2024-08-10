package com.ziadahmed.logintask

object Validation {

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }

    fun isValidUsername(username: String): Boolean {
        val usernameRegex = "^[A-Za-z]+$"
        return username.matches(usernameRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$"
        return password.matches(passwordRegex.toRegex())
    }
}