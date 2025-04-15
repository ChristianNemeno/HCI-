package com.android.hci.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.hci.R
import com.android.hci.utility.ExploreAdapter
import com.android.hci.utility.ExploreItem
import com.google.android.material.appbar.MaterialToolbar

class ExploreFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var exploreAdapter: ExploreAdapter
    private lateinit var toolbar: MaterialToolbar
    private lateinit var searchPlaceholder: TextView
    private lateinit var searchButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the updated layout
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        // --- Find Views ---
        recyclerView = view.findViewById(R.id.recycler_view_explore)
        toolbar = view.findViewById(R.id.toolbar_explore)
        searchPlaceholder = view.findViewById(R.id.search_placeholder) // Get search placeholder
        searchButton = view.findViewById(R.id.button_search_icon) // Get search icon button


        // --- Setup Toolbar (Optional: Add title, navigation, etc. if needed) ---
        // If you are using this fragment within MainActivity, MainActivity might handle the Toolbar.
        // If this fragment manages its own toolbar:
        // (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)


        // --- Setup RecyclerView ---
        setupRecyclerView()

        // --- Setup Hardcoded Search ---
        setupHardcodedSearch()


        return view
    }

    private fun setupRecyclerView() {
        // Create hardcoded data (Example with Philippines theme)
        val itemList = listOf(
            ExploreItem(R.drawable.discrete, "Discrete Mathematics", "Logic, set theory, combinatorics and graph theory."),
            ExploreItem(R.drawable.calculus, "Calculus II", "A continuation of Calculus I, focusing on techniques of integration, applications of the integral."),
            ExploreItem(R.drawable.la, "Linear Algebra", "A study of vectors, vector spaces, matrices, and linear transformations"),
            ExploreItem(R.drawable.im, "Information Management", "The collection, organization, storage, and retrieval of information"),
            ExploreItem(R.drawable.fundaprog, "Fundamentals of Programming", "Domain ni Arellano."),
            ExploreItem(R.drawable.dsa, "Data structures and Algorithms", "Focuses on organizing and processing data efficiently.")
            // Add more items as needed
            // Replace R.drawable.ic_launcher_background with actual document/image drawables
        )

        exploreAdapter = ExploreAdapter(itemList)
        recyclerView.layoutManager = LinearLayoutManager(context) // Set LayoutManager
        recyclerView.adapter = exploreAdapter // Set Adapter
    }


    private fun setupHardcodedSearch(){
        // Hardcoded action for the placeho lder click
        searchPlaceholder.setOnClickListener {
            Toast.makeText(context, "Search functionality is hardcoded for now.", Toast.LENGTH_SHORT).show()
            // In a real scenario, you'd navigate to a search screen or expand the search view.
            // For the prototype, we just show a message.
            // If you searched "philippines", you might filter the static list here,
            // but the request was just for the button functionality to be hardcoded.
        }
        // Hardcoded action for the search icon click
        searchButton.setOnClickListener{
            Toast.makeText(context, "Search Initiated (Hardcoded)", Toast.LENGTH_SHORT).show()
            // Perform the same dummy action or filtering if desired
        }

    }
}