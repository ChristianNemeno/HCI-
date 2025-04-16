package com.android.hci.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.hci.R // Make sure R is imported correctly

class CommunityAdapter(private val posts: MutableList<CommunityPost>) : // Use MutableList if votes change
    RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    // ViewHolder holds references to the views for each item
    class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.text_view_post_username)
        val timestampTextView: TextView = itemView.findViewById(R.id.text_view_post_timestamp)
        val titleTextView: TextView = itemView.findViewById(R.id.text_view_post_title)
        val contentTextView: TextView = itemView.findViewById(R.id.text_view_post_content)
        val postImageView: ImageView = itemView.findViewById(R.id.image_view_post_image)
        val upvoteButton: ImageButton = itemView.findViewById(R.id.button_upvote)
        val downvoteButton: ImageButton = itemView.findViewById(R.id.button_downvote)
        val voteCountTextView: TextView = itemView.findViewById(R.id.text_view_vote_count)
        val commentButton: ImageButton = itemView.findViewById(R.id.button_comments)
        val commentCountTextView: TextView = itemView.findViewById(R.id.text_view_comment_count)
        val shareButton: ImageButton = itemView.findViewById(R.id.button_share)
        val saveButton: ImageButton = itemView.findViewById(R.id.button_save)

        fun bind(post: CommunityPost) {
            usernameTextView.text = post.username
            timestampTextView.text = "• ${post.timestamp}"
            titleTextView.text = post.title

            // Handle optional content
            if (post.contentSnippet != null) {
                contentTextView.text = post.contentSnippet
                contentTextView.visibility = View.VISIBLE
            } else {
                contentTextView.visibility = View.GONE
            }

            // Handle optional image
            if (post.imageUrl != null) {
                postImageView.setImageResource(post.imageUrl)
                postImageView.visibility = View.VISIBLE
                val params = contentTextView.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
                params.topToBottom = R.id.image_view_post_image // Content below image
                contentTextView.requestLayout()
            } else {
                postImageView.visibility = View.GONE
                // Adjust content text constraints if image is gone
                val params = contentTextView.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
                params.topToBottom = R.id.text_view_post_title // Content below title
                contentTextView.requestLayout()
            }

            // Format and display votes/comments (simplified)
            voteCountTextView.text = (post.upvotes - post.downvotes).toString()
            commentCountTextView.text = "${post.commentCount} Comments"

            // --- Add Click Listeners (Prototype Actions) ---
            upvoteButton.setOnClickListener {
                // Basic prototype action: Increment upvote visually (doesn't persist)
                post.upvotes++
                voteCountTextView.text = (post.upvotes - post.downvotes).toString()
                // In a real app, update data source & notify adapter, maybe handle toggling
                Toast.makeText(itemView.context, "Upvoted (Prototype)", Toast.LENGTH_SHORT).show()
            }

            downvoteButton.setOnClickListener {
                post.downvotes++
                voteCountTextView.text = (post.upvotes - post.downvotes).toString()
                Toast.makeText(itemView.context, "Downvoted (Prototype)", Toast.LENGTH_SHORT).show()
            }

            commentButton.setOnClickListener {
                Toast.makeText(itemView.context, "View Comments (Prototype)", Toast.LENGTH_SHORT).show()
            }

            shareButton.setOnClickListener {
                Toast.makeText(itemView.context, "Share (Prototype)", Toast.LENGTH_SHORT).show()
            }

            saveButton.setOnClickListener {
                Toast.makeText(itemView.context, "Save (Prototype)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.community_post_item, parent, false) // Use your item layout
        return CommunityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
        // Optional: Add click listener to the whole item if needed
        // holder.itemView.setOnClickListener { /* ... */ }
    }

    override fun getItemCount() = posts.size
}