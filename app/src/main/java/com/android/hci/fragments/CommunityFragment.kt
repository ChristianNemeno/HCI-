package com.android.hci.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.hci.R // Make sure R is imported
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
        val samplePosts = mutableListOf(
            CommunityPost("u/LearnifyGuru", "1h ago", "Welcome to the Learnify Community!", "Share your thoughts, ask questions, and connect with fellow learners.", null, 15, 1, 5),
            CommunityPost("u/StudyBuddy123", "3h ago", "Tips for Calculus II Integration?", null, R.drawable.calculus, 25, 2, 12), // Example using drawable
            CommunityPost("u/CodeWizard", "5h ago", "Understanding Data Structures", "Let's discuss the best ways to implement linked lists.", null, 8, 0, 3),
            CommunityPost("u/HistoryFan", "1d ago", "Interesting fact about Philippine History", "Did you know...", R.drawable.philhis, 42, 5, 18),
            CommunityPost("u/EcoNomist", "2d ago", "The Economy as Instituted Process - Thoughts?", "Reading this paper and wanted to discuss...", null, 5, 1, 2)

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