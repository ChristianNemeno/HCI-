package com.android.hci.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import coil.load
import com.android.hci.R

class MyContentDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_content_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleTextView: TextView = view.findViewById(R.id.detail_content_title)
        val imageView: ImageView = view.findViewById(R.id.detail_content_image)
        val contentTextView: TextView = view.findViewById(R.id.detail_content_text)

        // Retrieve arguments
        val title = arguments?.getString(ARG_TITLE)
        val imageUriString = arguments?.getString(ARG_IMAGE_URI)
        val textContent = arguments?.getString(ARG_TEXT_CONTENT)

        // Populate views based on available data
        if (!title.isNullOrEmpty()) {
            titleTextView.text = title
            titleTextView.visibility = View.VISIBLE
        } else {
            titleTextView.visibility = View.GONE
        }

        if (!imageUriString.isNullOrEmpty()) {
            try {
                imageView.visibility = View.VISIBLE
                imageView.load(imageUriString.toUri()) {
                    placeholder(R.drawable.ic_launcher_background)
                    error(R.drawable.ic_create)
                }
            } catch (e: Exception) {
                imageView.setImageResource(R.drawable.ic_create) // Show error placeholder
                imageView.visibility = View.VISIBLE
            }
        } else {
            imageView.visibility = View.GONE
        }

        if (!textContent.isNullOrEmpty()) {
            contentTextView.text = textContent
            contentTextView.visibility = View.VISIBLE
            // Adjust contentTextView's top constraint if title is gone but image is present
            val params = contentTextView.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
            if (titleTextView.visibility == View.GONE && imageView.visibility == View.VISIBLE) {
                params.topToBottom = R.id.detail_content_image
            } else if (titleTextView.visibility == View.VISIBLE){
                params.topToBottom = if (imageView.visibility == View.VISIBLE) R.id.detail_content_image else R.id.detail_content_title
            } else { // Both title and image are gone
                params.topToTop = androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID // Align to top if needed
            }
            contentTextView.requestLayout()

        } else {
            contentTextView.visibility = View.GONE
        }
    }

    companion object {
        private const val ARG_TITLE = "content_title"
        private const val ARG_IMAGE_URI = "content_image_uri"
        private const val ARG_TEXT_CONTENT = "content_text"

        // Factory method to create fragment with arguments
        fun newInstance(
            title: String?,
            imageUriString: String?,
            textContent: String?
        ): MyContentDetailFragment {
            val fragment = MyContentDetailFragment()
            val args = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_IMAGE_URI, imageUriString)
                putString(ARG_TEXT_CONTENT, textContent)
            }
            fragment.arguments = args
            return fragment
        }
    }
}