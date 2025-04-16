package com.android.hci.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit // For fragment navigation
import com.android.hci.R
import com.android.hci.utility.MyApplication // Import MyApplication
import com.android.hci.utility.MyContentItem // Import your data class
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.UUID // For generating IDs if needed in MyContentItem

class CreateFragment : Fragment() {

    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    private lateinit var myApp: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myApp = requireActivity().applicationContext as MyApplication

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val imageUriString = uri.toString()
                showToast("Image Selected: $imageUriString")

                // Create a new content item
                // Using URI string as a temporary title, or generate one
                val newItem = MyContentItem(
                    imageUriString = imageUriString
                    // title = "Content ${myApp.myContentList.size + 1}" // Example title
                )

                // Add the new item to the list in MyApplication
                myApp.myContentList.add(newItem)
                showToast("Content added to 'My Content' list (Prototype)")

            } else {
                // User cancelled the picker or there was an error
                showToast("Image selection cancelled.")
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the views
        val createContentCard: CardView = view.findViewById(R.id.card_create_content)
        val resourcesCard: CardView = view.findViewById(R.id.card_resources)
        val localFilesCard: CardView = view.findViewById(R.id.card_local_files)
        val myContentPlaceholderCard: CardView = view.findViewById(R.id.card_my_content_placeholder) // Find the new card
        val fab: FloatingActionButton = view.findViewById(R.id.fab_create)

        // --- Placeholder Click Listeners for top cards (Unchanged) ---
        createContentCard.setOnClickListener {
            showToast("Create Content Card Clicked (Prototype)")
            // TODO: Implement actual navigation or action
        }
        resourcesCard.setOnClickListener {
            showToast("Resources Card Clicked (Prototype)")
            // TODO: Implement actual navigation or action
        }
        localFilesCard.setOnClickListener {
            showToast("Local Files Card Clicked (Prototype)")
            // TODO: Implement actual navigation or action
        }

        // --- **MODIFIED**: Click Listener for "My Content" Card ---
        myContentPlaceholderCard.setOnClickListener {
            showToast("Navigating to My Content List (Prototype)")
            // Navigate to the new MyContentListFragment
            parentFragmentManager.commit {
                replace(R.id.fragment_container, MyContentListFragment.newInstance()) // Use the actual container ID from MainActivity
                addToBackStack(null) // Allows user to navigate back to CreateFragment
                setReorderingAllowed(true)
            }
            // TODO: Create MyContentListFragment
        }


        // --- **MODIFIED**: Click Listener for FAB ---
        fab.setOnClickListener {
            // Launch the image picker
            showToast("Opening image gallery...")
            pickImageLauncher.launch("image/*") // Use the launcher

            // --- Removed the old dialog logic ---
            // showPublishDialog() // No longer showing the static dialog
        }
    }

    // --- Helper function to show Toast messages ---
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    // --- The showPublishDialog function is no longer needed by the FAB ---
    // private fun showPublishDialog() { ... }


    // Optional: Companion object
    companion object {
        fun newInstance(): CreateFragment {
            return CreateFragment()
        }
    }
}