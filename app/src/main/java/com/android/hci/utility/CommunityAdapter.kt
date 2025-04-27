package com.android.hci.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity // Import AppCompatActivity
import androidx.fragment.app.commit // Import commit extension function
import androidx.recyclerview.widget.RecyclerView
import com.android.hci.R // Make sure R is imported correctly
import com.android.hci.fragments.CommentFragment // Import CommentFragment

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
                // Adjust content text constraints if image is present
                val contentParams = contentTextView.layoutParams as? androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
                contentParams?.topToBottom = R.id.image_view_post_image // Content below image
                contentTextView.requestLayout()

                // Adjust actions layout constraints if image is present
                val actionsParams = itemView.findViewById<View>(R.id.layout_actions).layoutParams as? androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
                actionsParams?.topToBottom = if(contentTextView.visibility == View.VISIBLE) R.id.text_view_post_content else R.id.image_view_post_image
                itemView.findViewById<View>(R.id.layout_actions).requestLayout()

            } else {
                postImageView.visibility = View.GONE
                // Adjust content text constraints if image is gone
                val contentParams = contentTextView.layoutParams as? androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
                contentParams?.topToBottom = R.id.text_view_post_title // Content below title
                contentTextView.requestLayout()

                // Adjust actions layout constraints if image is gone
                val actionsParams = itemView.findViewById<View>(R.id.layout_actions).layoutParams as? androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
                actionsParams?.topToBottom = if(contentTextView.visibility == View.VISIBLE) R.id.text_view_post_content else R.id.text_view_post_title
                itemView.findViewById<View>(R.id.layout_actions).requestLayout()
            }


            // Adjust top margin of actions layout based on whether contentTextView is visible
            val actionsLayout = itemView.findViewById<View>(R.id.layout_actions)
            val paramsActions = actionsLayout.layoutParams as ViewGroup.MarginLayoutParams
            paramsActions.topMargin = if (contentTextView.visibility == View.VISIBLE || postImageView.visibility == View.VISIBLE) {
                itemView.context.resources.getDimensionPixelSize(R.dimen.actions_margin_top) // Define this dimension in dimens.xml (e.g., 12dp)
            } else {
                itemView.context.resources.getDimensionPixelSize(R.dimen.actions_margin_top_condensed) // Define this (e.g., 8dp)
            }
            actionsLayout.layoutParams = paramsActions



            // Format and display votes/comments (simplified)
            voteCountTextView.text = (post.upvotes - post.downvotes).toString()
            commentCountTextView.text = "${post.commentCount} Comments" // Use the count from data class

            // --- Add Click Listeners (Prototype Actions) ---
            upvoteButton.setOnClickListener {
                // Basic prototype action: Increment upvote visually (doesn't persist)
                post.upvotes++ // Modify the data object
                voteCountTextView.text = (post.upvotes - post.downvotes).toString() // Update UI
                // In a real app, update data source & notify adapter, maybe handle toggling
                Toast.makeText(itemView.context, "Upvoted (Prototype)", Toast.LENGTH_SHORT).show()
            }

            downvoteButton.setOnClickListener {
                post.downvotes++ // Modify the data object
                voteCountTextView.text = (post.upvotes - post.downvotes).toString() // Update UI
                Toast.makeText(itemView.context, "Downvoted (Prototype)", Toast.LENGTH_SHORT).show()
            }

            // --- MODIFIED: Comment Button Click Listener ---
            commentButton.setOnClickListener {
                // Get the FragmentManager
                val fragmentManager = (itemView.context as? AppCompatActivity)?.supportFragmentManager
                if (fragmentManager != null) {
                    // Create the CommentFragment instance
                    val commentFragment = CommentFragment.newInstance(post.title, post.comments)

                    // Perform the fragment transaction
                    fragmentManager.commit {
                        replace(R.id.fragment_container, commentFragment) // Use your main container ID
                        addToBackStack(null) // Add to back stack so user can return
                        setReorderingAllowed(true)
                    }
                } else {
                    // Fallback or error handling if context is not an AppCompatActivity
                    Toast.makeText(itemView.context, "Error navigating to comments", Toast.LENGTH_SHORT).show()
                }
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
        // Add default dimensions resource file if not present
        if (parent.context.resources.getIdentifier("actions_margin_top", "dimen", parent.context.packageName) == 0) {
            // Create dimens.xml if it doesn't exist or add these values
            // This part is tricky to automate here, suggest manually adding:
            // <dimen name="actions_margin_top">12dp</dimen>
            // <dimen name="actions_margin_top_condensed">8dp</dimen>
            // to res/values/dimens.xml
            println("WARNING: Please define 'actions_margin_top' and 'actions_margin_top_condensed' in res/values/dimens.xml")
        }
        return CommunityViewHolder(view)
    }


    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount() = posts.size
}

// --- Add dimens.xml (res/values/dimens.xml) ---
// Create this file if it doesn't exist
/*
<resources>
    <dimen name="actions_margin_top">12dp</dimen>
    <dimen name="actions_margin_top_condensed">8dp</dimen>
</resources>
*/