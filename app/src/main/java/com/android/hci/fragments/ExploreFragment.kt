package com.android.hci.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.hci.R

class ExploreFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Replace with actual ExploreFragment layout when implemented
        return inflater.inflate(R.layout.fragment_place_holder, container, false).apply {
            findViewById<TextView>(R.id.placeholder_text).text = "Explore Section"
        }
    }
}