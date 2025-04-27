package com.android.hci.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.hci.R
import com.android.hci.utility.Comment
import com.android.hci.utility.CommentAdapter
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.parcelize.Parcelize // Import Parcelize
import java.util.ArrayList // Import ArrayList


// --- Make Comment Parcelable ---
// You might need to add the 'kotlin-parcelize' plugin in build.gradle
@Parcelize
data class CommentParcelable(
    val username: String,
    val text: String,
    val timestamp: String
) : Parcelable

// --- Convert Comment to CommentParcelable ---
fun Comment.toParcelable(): CommentParcelable {
    return CommentParcelable(username, text, timestamp)
}

// --- Convert CommentParcelable back to Comment ---
fun CommentParcelable.toComment(): Comment {
    return Comment(username, text, timestamp)
}


class CommentFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var noCommentsTextView: TextView
    private lateinit var toolbar: MaterialToolbar
    private var commentsList: List<Comment> = emptyList()
    private var postTitle: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postTitle = it.getString(ARG_POST_TITLE)
            // Retrieve the Parcelable ArrayList and convert back
            val parcelableComments = it.getParcelableArrayList<CommentParcelable>(ARG_COMMENTS)
            commentsList = parcelableComments?.map { it.toComment() } ?: emptyList()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_comments, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_comments)
        noCommentsTextView = view.findViewById(R.id.text_view_no_comments)
        toolbar = view.findViewById(R.id.toolbar_comments)

        setupToolbar()
        setupRecyclerView()
        checkEmptyState()

        return view
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        toolbar.title = postTitle ?: "Comments" // Set the post title
        toolbar.setNavigationOnClickListener {
            // Handle back press to return to the CommunityFragment
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupRecyclerView() {
        commentAdapter = CommentAdapter(commentsList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = commentAdapter
    }

    private fun checkEmptyState() {
        if (commentsList.isEmpty()) {
            recyclerView.visibility = View.GONE
            noCommentsTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            noCommentsTextView.visibility = View.GONE
        }
    }

    companion object {
        private const val ARG_POST_TITLE = "post_title"
        private const val ARG_COMMENTS = "post_comments"

        fun newInstance(title: String, comments: List<Comment>): CommentFragment {
            val fragment = CommentFragment()
            val args = Bundle().apply {
                putString(ARG_POST_TITLE, title)
                // Convert List<Comment> to ArrayList<CommentParcelable>
                val parcelableComments = ArrayList(comments.map { it.toParcelable() })
                putParcelableArrayList(ARG_COMMENTS, parcelableComments)
            }
            fragment.arguments = args
            return fragment
        }
    }
}