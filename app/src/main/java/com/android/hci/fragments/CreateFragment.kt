package com.android.hci.fragments

import android.net.Uri
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.hci.R
import com.android.hci.utility.MyApplication
import com.android.hci.utility.MyContentItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CreateFragment : Fragment() {

    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private lateinit var myApp: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myApp = requireActivity().applicationContext as MyApplication

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val imageUriString = uri.toString()
                val newItem = MyContentItem(
                    imageUriString = imageUriString
                    // No title or text content when adding image this way
                )
                myApp.myContentList.add(newItem)
                showToast("Image added to 'My Content' list (Prototype)")

            } else {
                showToast("Image selection cancelled.")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val createContentCard: CardView = view.findViewById(R.id.card_create_content)
        val resourcesCard: CardView = view.findViewById(R.id.card_resources)
        val localFilesCard: CardView = view.findViewById(R.id.card_local_files)
        val myContentPlaceholderCard: CardView = view.findViewById(R.id.card_my_content_placeholder)
        val fab: FloatingActionButton = view.findViewById(R.id.fab_create)

        // --- **MODIFIED**: Click Listener for "Create Content" Card ---
        createContentCard.setOnClickListener {
            // Navigate to the new ArticleEditorFragment
            parentFragmentManager.commit {
                replace(R.id.fragment_container, ArticleEditorFragment.newInstance()) // Use the actual container ID
                addToBackStack(null) // Allows user to navigate back
                setReorderingAllowed(true)
            }
        }

        // Other listeners remain the same...
        resourcesCard.setOnClickListener {
            showToast("Resources Card Clicked (Prototype)")
        }
        localFilesCard.setOnClickListener {
            showToast("Local Files Card Clicked (Prototype)")
        }
        myContentPlaceholderCard.setOnClickListener {
            // This listener remains the same - navigates to MyContentListFragment
            showToast("Navigating to My Content List (Prototype)")
            parentFragmentManager.commit {
                replace(R.id.fragment_container, MyContentListFragment.newInstance())
                addToBackStack(null)
                setReorderingAllowed(true)
            }
        }
        fab.setOnClickListener {
            showToast("Opening image gallery...")
            pickImageLauncher.launch("image/*")
        }
    }

    // --- REMOVED showCreateTextDialog() function ---

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): CreateFragment {
            return CreateFragment()
        }
    }
}