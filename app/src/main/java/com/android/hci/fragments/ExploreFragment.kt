package com.android.hci.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher // Import TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText // Import EditText
import android.widget.ImageButton
// import android.widget.TextView // No longer needed for search placeholder
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.hci.R
import com.android.hci.utility.ExploreAdapter
import com.android.hci.utility.ExploreItem
import com.android.hci.utility.OnExploreItemClickListener
import com.google.android.material.appbar.MaterialToolbar

class ExploreFragment : Fragment(), OnExploreItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var exploreAdapter: ExploreAdapter
    private lateinit var toolbar: MaterialToolbar
    // MODIFIED: Changed from TextView to EditText
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: ImageButton

    // --- NEW: Store the full list ---
    private val fullItemList = listOf(
        ExploreItem(R.drawable.discrete, "Discrete Mathematics", "Logic, set theory, combinatorics and graph theory."),
        ExploreItem(R.drawable.calculus, "Calculus II", "A continuation of Calculus I, focusing on techniques of integration, applications of the integral."),
        ExploreItem(R.drawable.la, "Linear Algebra", "A study of vectors, vector spaces, matrices, and linear transformations"),
        ExploreItem(R.drawable.im, "Information Management", "The collection, organization, storage, and retrieval of information"),
        ExploreItem(R.drawable.fundaprog, "Fundamentals of Programming", "Domain ni Arellano."),
        ExploreItem(R.drawable.dsa, "Data structures and Algorithms", "Focuses on organizing and processing data efficiently.")
        // Add more items as needed
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        // --- Find Views ---
        recyclerView = view.findViewById(R.id.recycler_view_explore)
        toolbar = view.findViewById(R.id.toolbar_explore)
        // MODIFIED: Find EditText instead of TextView
        searchEditText = view.findViewById(R.id.search_edit_text)
        searchButton = view.findViewById(R.id.button_search_icon)

        // --- Setup RecyclerView (using full list initially) ---
        setupRecyclerView()

        // --- Setup Search Logic ---
        setupSearch() // Renamed from setupHardcodedSearch

        return view
    }

    private fun setupRecyclerView() {
        // Use the full list initially
        exploreAdapter = ExploreAdapter(fullItemList.toMutableList(), this) // Pass the full list
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = exploreAdapter
    }


    // MODIFIED: Renamed and implemented actual filtering
    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterList(s.toString())
            }
        })

        // Keep the button to trigger search manually as well (optional)
        searchButton.setOnClickListener {
            filterList(searchEditText.text.toString())
            // Optionally hide keyboard here
        }
    }

    // --- NEW: Filtering function ---
    private fun filterList(query: String) {
        val filteredList = if (query.isBlank()) {
            fullItemList // Show all if query is empty
        } else {
            fullItemList.filter {
                it.title.contains(query, ignoreCase = true) // Filter by title (case-insensitive)
                // You could also filter by description: || it.description.contains(query, ignoreCase = true)
            }
        }
        exploreAdapter.updateList(filteredList) // Update the adapter
    }

    private fun navigateToDetailFragment(item: ExploreItem) {
        val detailFragment = DetailFragment.newInstance(item.title, item.description, item.imageResId)
        parentFragmentManager.commit {
            replace(R.id.fragment_container, detailFragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }

    override fun onExploreItemClick(item: ExploreItem) {
        navigateToDetailFragment(item)
    }
}