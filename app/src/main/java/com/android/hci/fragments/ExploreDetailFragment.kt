package com.android.hci.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.hci.R // Make sure R is imported

class ExploreDetailFragment : Fragment() {

    // Arguments keys
    companion object {
        private const val ARG_TITLE = "item_title"
        private const val ARG_DESCRIPTION = "item_description"
        private const val ARG_IMAGE_RES_ID = "item_image_res_id"

        // Factory method to create a new instance with arguments
        fun newInstance(title: String, description: String, @androidx.annotation.DrawableRes imageResId: Int): ExploreDetailFragment {
            val fragment = ExploreDetailFragment()
            val args = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_DESCRIPTION, description)
                putInt(ARG_IMAGE_RES_ID, imageResId)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment (You need to create this layout file)
        val view = inflater.inflate(R.layout.explore_detail_fragment, container, false) // Create this layout

        val titleTextView: TextView = view.findViewById(R.id.detail_text_view_title)
        val descriptionTextView: TextView = view.findViewById(R.id.detail_text_view_description)
        val imageView: ImageView = view.findViewById(R.id.detail_image_view)

        // Retrieve arguments
        arguments?.let {
            titleTextView.text = it.getString(ARG_TITLE)
            descriptionTextView.text = it.getString(ARG_DESCRIPTION)
            imageView.setImageResource(it.getInt(ARG_IMAGE_RES_ID))
        }

        return view
    }
}