package com.android.hci.utility

import androidx.annotation.DrawableRes

data class ExploreItem(
    @DrawableRes val imageResId: Int, // Use @DrawableRes for image resources
    val title: String,
    val description: String
)

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

interface OnExploreItemClickListener {
    fun onExploreItemClick(item: ExploreItem)
}

