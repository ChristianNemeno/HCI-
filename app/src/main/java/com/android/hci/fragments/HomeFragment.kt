package com.android.hci.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.hci.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val exploreButton = view.findViewById<LinearLayout>(R.id.explore_button_home)
        val communityButton = view.findViewById<LinearLayout>(R.id.community_button_home)
        val savedButton = view.findViewById<LinearLayout>(R.id.saved_button_home)
        val createButton = view.findViewById<LinearLayout>(R.id.create_button_home)

        exploreButton.setOnClickListener {
            navigateToFragment(ExploreFragment())
        }

        communityButton.setOnClickListener {
            navigateToFragment(PlaceholderFragment.newInstance("Community"))
        }

        savedButton.setOnClickListener {
            navigateToFragment(SavedFragment())
        }

        createButton.setOnClickListener {
            navigateToFragment(PlaceholderFragment.newInstance("Create"))
        }

        return view
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }
}