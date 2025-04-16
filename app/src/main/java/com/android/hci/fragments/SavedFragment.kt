package com.android.hci.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
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

class SavedFragment : Fragment(), OnExploreItemClickListener {

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
        val view = inflater.inflate(R.layout.fragment_saved, container, false)

        // --- Find Views ---
        recyclerView = view.findViewById(R.id.recycler_view_saved)
        toolbar = view.findViewById(R.id.toolbar_saved)
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
            ExploreItem(R.drawable.energy, "\n" + "Renewable and Sustainable Energy Reviews", "The mission of Renewable and Sustainable Energy Reviews is to communicate the most interesting and relevant critical thinking in renewable and sustainable energy in order to bring together the research community"),
            ExploreItem(R.drawable.tamaraw, "Field survey on Tamaraw", "Tamaraw, an endemic species on the Philippine island of Mindoro, is a critically endangered animal listed by IUCN."),
            ExploreItem(R.drawable.eco, "The Economy as Instituted Process", "The economy embodied in institutions that cause individual choices to give rise to interdependent movements that constitute the economic process. Assuming that the choice is induced by an insufficiency of the means, the logic of rational action turns into that variant of the theory of choice called formal economics."),
            ExploreItem(R.drawable.philhis, "Philippine history", "Philippine History is the story of the Filipino people's struggle for freedom, identity, and nationhood. It begins with early indigenous cultures and societies, followed by over 300 years of Spanish colonization, American rule, and Japanese occupation. After gaining independence in 1946, the Philippines has since evolved through political, social, and economic changes, shaping its unique blend of Eastern and Western influences and a resilient democratic spirit."),

            // Add more items as needed
            // Replace R.drawable.ic_launcher_background with actual document/image drawables
        )

        exploreAdapter = ExploreAdapter(itemList, this)
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
    private fun navigateToDetailFragment(item: ExploreItem) {
        // Create the detail fragment instance
        val detailFragment = DetailFragment.newInstance(item.title, item.description, item.imageResId)

        // Use FragmentManager to replace the current fragment
        parentFragmentManager.commit {
            replace(R.id.fragment_container, detailFragment) // R.id.fragment_container from activity_main.xml
            addToBackStack(null) // Add transaction to back stack to allow back navigation
            setReorderingAllowed(true)
        }
    }

    override fun onExploreItemClick(item: ExploreItem) {
        // Navigate to the Detail Fragment/Activity here
        navigateToDetailFragment(item) // Call the navigation function
    }
}