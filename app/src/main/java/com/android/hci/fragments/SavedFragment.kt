package com.android.hci.fragments

import android.os.Bundle
import android.text.Editable // Import TextWatcher
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText // Import EditText
import android.widget.ImageButton
// import android.widget.TextView // No longer needed for placeholder
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.hci.R
import com.android.hci.utility.ExploreAdapter // Uses the same adapter
import com.android.hci.utility.ExploreItem // Uses the same item type
import com.android.hci.utility.OnExploreItemClickListener
import com.google.android.material.appbar.MaterialToolbar

class SavedFragment : Fragment(), OnExploreItemClickListener {

    private lateinit var recyclerView: RecyclerView
    // Uses the same adapter as ExploreFragment
    private lateinit var exploreAdapter: ExploreAdapter
    private lateinit var toolbar: MaterialToolbar
    // MODIFIED: Changed from TextView to EditText
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: ImageButton

    // --- NEW: Store the full list for Saved items ---
    private val fullItemList = listOf(
        ExploreItem(R.drawable.energy, "Renewable and Sustainable Energy Reviews", "The mission of Renewable and Sustainable Energy Reviews is to communicate the most interesting and relevant critical thinking in renewable and sustainable energy in order to bring together the research community"),
        ExploreItem(R.drawable.tamaraw, "Field survey on Tamaraw", "Tamaraw, an endemic species on the Philippine island of Mindoro, is a critically endangered animal listed by IUCN."),
        ExploreItem(R.drawable.eco, "The Economy as Instituted Process", "The economy embodied in institutions that cause individual choices to give rise to interdependent movements that constitute the economic process. Assuming that the choice is induced by an insufficiency of the means, the logic of rational action turns into that variant of the theory of choice called formal economics."),
        ExploreItem(R.drawable.philhis, "Philippine history", "Philippine History is the story of the Filipino people's struggle for freedom, identity, and nationhood. It begins with early indigenous cultures and societies, followed by over 300 years of Spanish colonization, American rule, and Japanese occupation. After gaining independence in 1946, the Philippines has since evolved through political, social, and economic changes, shaping its unique blend of Eastern and Western influences and a resilient democratic spirit."),
        // Add more saved items as needed
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the updated layout
        val view = inflater.inflate(R.layout.fragment_saved, container, false) // Use fragment_saved layout

        // --- Find Views ---
        recyclerView = view.findViewById(R.id.recycler_view_saved)
        toolbar = view.findViewById(R.id.toolbar_saved)
        // MODIFIED: Find EditText and use updated IDs
        searchEditText = view.findViewById(R.id.search_edit_text_saved)
        searchButton = view.findViewById(R.id.button_search_icon_saved)

        // --- Setup RecyclerView (using full list initially) ---
        setupRecyclerView()

        // --- Setup Search Logic ---
        setupSearch() // Renamed from setupHardcodedSearch

        return view
    }

    private fun setupRecyclerView() {
        // Create hardcoded data (Example with Philippines theme) - Moved list to fullItemList member

        // Initialize adapter with the full list
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