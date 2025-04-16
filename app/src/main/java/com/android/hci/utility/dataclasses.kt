package com.android.hci.utility

import androidx.annotation.DrawableRes

// Add this within the dataclasses.kt file
data class CommunityPost(
    val username: String,
    val timestamp: String, // Simple string for prototype (e.g., "2h ago")
    val title: String,
    val contentSnippet: String?, // Optional text content
    val imageUrl: Int?, // Optional: Use DrawableRes if using local images like ExploreItem
    var upvotes: Int,
    var downvotes: Int,
    val commentCount: Int
)


data class ExploreItem(
    @DrawableRes val imageResId: Int, // Use @DrawableRes for image resources
    val title: String,
    val description: String
)
data class SavedItem(
    val imageIdentifier: String, // Could be a URL or resource name
    val title: String,
    val description: String
    // Add any other relevant fields like source URL, saved date etc.
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

