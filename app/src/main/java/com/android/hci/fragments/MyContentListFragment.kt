package com.android.hci.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit // Import commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.hci.R
import com.android.hci.utility.MyApplication
import com.android.hci.utility.MyContentAdapter
import com.android.hci.utility.MyContentItem // Import Item
import com.android.hci.utility.OnMyContentItemClickListener // Import Listener

// Implement the listener interface
class MyContentListFragment : Fragment(), OnMyContentItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myContentAdapter: MyContentAdapter
    private lateinit var myApp: MyApplication

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_content_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myApp = requireActivity().applicationContext as MyApplication
        recyclerView = view.findViewById(R.id.recycler_view_my_content)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val contentList = myApp.myContentList

        // Pass 'this' as the listener to the adapter
        myContentAdapter = MyContentAdapter(contentList, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = myContentAdapter
    }

    override fun onResume() {
        super.onResume()
        // Refresh list on resume
        if (::myApp.isInitialized && ::recyclerView.isInitialized) {
            myContentAdapter = MyContentAdapter(myApp.myContentList, this) // Pass listener again
            recyclerView.adapter = myContentAdapter
        }
    }

    // --- Implementation of the click listener interface ---
    override fun onMyContentItemClick(item: MyContentItem) {
        // Navigate to the Detail Fragment, passing the item's data
        parentFragmentManager.commit {
            replace(
                R.id.fragment_container, // Make sure this is your container ID in MainActivity
                MyContentDetailFragment.newInstance(
                    title = item.title,
                    imageUriString = item.imageUriString,
                    textContent = item.textContent
                )
            )
            addToBackStack(null) // Allow navigating back to the list
            setReorderingAllowed(true)
        }
    }

    companion object {
        fun newInstance(): MyContentListFragment {
            return MyContentListFragment()
        }
    }
}