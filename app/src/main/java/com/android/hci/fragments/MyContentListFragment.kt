package com.android.hci.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.hci.R
import com.android.hci.utility.MyApplication
import com.android.hci.utility.MyContentAdapter

class MyContentListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myContentAdapter: MyContentAdapter
    private lateinit var myApp: MyApplication

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_content_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myApp = requireActivity().applicationContext as MyApplication
        recyclerView = view.findViewById(R.id.recycler_view_my_content)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Get the list from MyApplication
        val contentList = myApp.myContentList

        myContentAdapter = MyContentAdapter(contentList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = myContentAdapter

        // Optional: Handle empty list state (show a message if list is empty)
    }

    // Notify adapter when returning to fragment (optional, but good practice)
    // This helps refresh the list if you add an item and immediately navigate here
    override fun onResume() {
        super.onResume()
        if (::myContentAdapter.isInitialized) {
            // Get the latest list and notify adapter
            myContentAdapter = MyContentAdapter(myApp.myContentList)
            recyclerView.adapter = myContentAdapter
            // Or if using DiffUtil: myContentAdapter.submitList(myApp.myContentList)
        }
    }


    companion object {
        fun newInstance(): MyContentListFragment {
            return MyContentListFragment()
        }
    }
}