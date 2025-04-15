package com.android.hci


data class PrototypeUser(
    val name: String?,
    val email: String,
    val phone: String?, // Added phone
    val dob: String?,   // Added DOB
    val password_insecure: String // Storing password directly - INSECURE PROTOTYPE ONLY
)

// Data class to hold the current logged-in session state
data class UserSession(
    var isLoggedIn: Boolean = false,
    var userName: String? = null,
    var userEmail: String? = null
)