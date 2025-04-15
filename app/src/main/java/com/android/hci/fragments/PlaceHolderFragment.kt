package com.android.hci.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.hci.R

class PlaceholderFragment : Fragment() {
    companion object {
        fun newInstance(section: String): PlaceholderFragment {
            val fragment = PlaceholderFragment()
            val args = Bundle()
            args.putString("section", section)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_place_holder, container, false)
        val textView = view.findViewById<TextView>(R.id.placeholder_text)
        val section = arguments?.getString("section") ?: "Unknown"
        textView.text = "Placeholder for $section"
        return view
    }


}