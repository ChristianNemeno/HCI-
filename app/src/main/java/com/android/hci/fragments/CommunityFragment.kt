package com.android.hci.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.hci.R // Make sure R is imported
import com.android.hci.utility.Comment // Import the new Comment class
import com.android.hci.utility.CommunityAdapter
import com.android.hci.utility.CommunityPost

class CommunityFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var communityAdapter: CommunityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_community)
        setupRecyclerView()

        return view
    }

    private fun setupRecyclerView() {
        // --- Create Sample Data (Prototype) ---
        // --- MODIFIED: Added hardcoded comments to the first post ---
        val samplePosts = mutableListOf(
            CommunityPost(
                username = "u/LearnifyGuru",
                timestamp = "1h ago",
                title = "Welcome to the Learnify Community!",
                contentSnippet = "Share your thoughts, ask questions, and connect with fellow learners.",
                imageUrl = null,
                upvotes = 15,
                downvotes = 1,
                commentCount = 2, // Update this count to match actual comments added
                comments = listOf( // Add comments for the first post
                    Comment("u/Alice", "Great to be here!", "55m ago"),
                    Comment("u/Bob", "Looking forward to learning.", "30m ago")
                )
            ),
            CommunityPost(
                username = "u/StudyBuddy123",
                timestamp = "3h ago",
                title = "Tips for Calculus II Integration?",
                contentSnippet = null,
                imageUrl = R.drawable.calculus, // Example using drawable
                upvotes = 25,
                downvotes = 2,
                commentCount = 12,
                comments = emptyList() // No comments for this post
            ),
            CommunityPost(
                username = "u/CodeWizard",
                timestamp = "5h ago",
                title = "Understanding Data Structures",
                contentSnippet = "Let's discuss the best ways to implement linked lists.",
                imageUrl = null,
                upvotes = 8,
                downvotes = 0,
                commentCount = 3,
                comments = emptyList() // No comments
            ),
            CommunityPost(
                username = "u/HistoryFan",
                timestamp = "1d ago",
                title = "Interesting fact about Philippine History",
                contentSnippet = "Did you know...",
                imageUrl = R.drawable.philhis, // Example using drawable
                upvotes = 42,
                downvotes = 5,
                commentCount = 18,
                comments = emptyList() // No comments
            ),
            CommunityPost(
                username = "u/EcoNomist",
                timestamp = "2d ago",
                title = "The Economy as Instituted Process - Thoughts?",
                contentSnippet = "Reading this paper and wanted to discuss...",
                imageUrl = null,
                upvotes = 5,
                downvotes = 1,
                commentCount = 2,
                comments = emptyList() // No comments
            )
        )

        communityAdapter = CommunityAdapter(samplePosts) // Pass mutable list
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = communityAdapter
    }

    // Optional: If you need a static factory method like PlaceholderFragment
    companion object {
        fun newInstance(): CommunityFragment {
            return CommunityFragment()
        }
    }
}