package com.android.hci

import android.app.Application



class MyApplication : Application() {
    // Data class to hold user information including password for the prototype


    // Map to store registered users (Email -> User Details) - PROTOTYPE ONLY
    val registeredUsers = mutableMapOf<String, PrototypeUser>()

    // Instance to hold the current user session data
    var currentUserSession: UserSession? = null

    override fun onCreate() {
        super.onCreate()
        // Initialize session as logged out
        currentUserSession = UserSession(isLoggedIn = false)
    }

    // --- Registration Logic (Prototype) ---
    fun registerUser(user: PrototypeUser): Boolean {
        if (registeredUsers.containsKey(user.email)) {
            return false // User already exists
        }
        registeredUsers[user.email] = user
        return true // Registration successful
    }

    // --- Login Logic (Prototype) ---
    fun loginUser(email: String, passwordToCheck: String): Boolean {
        val user = registeredUsers[email]
        if (user != null && user.password_insecure == passwordToCheck) {
            // Login successful - Start session
            startUserSession(user.name, user.email)
            return true
        }
        // Login failed - Ensure session is ended
        endUserSession()
        return false
    }

    // Function to start a user session
    fun startUserSession(name: String?, email: String?) {
        currentUserSession = UserSession(isLoggedIn = true, userName = name, userEmail = email)
    }

    // Function to end a user session
    fun endUserSession() {
        currentUserSession = UserSession(isLoggedIn = false, userName = null, userEmail = null)
    }

    // Function to check if a user is logged in
    fun isUserLoggedIn(): Boolean {
        return currentUserSession?.isLoggedIn ?: false
    }
}