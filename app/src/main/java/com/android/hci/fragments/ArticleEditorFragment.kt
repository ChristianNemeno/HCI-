package com.android.hci.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.hci.R
import com.android.hci.utility.MyApplication
import com.android.hci.utility.MyContentItem

class ArticleEditorFragment : Fragment() {

    private lateinit var myApp: MyApplication
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myApp = requireActivity().applicationContext as MyApplication
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleEditText = view.findViewById(R.id.edit_text_article_title)
        contentEditText = view.findViewById(R.id.edit_text_article_content)
        saveButton = view.findViewById(R.id.button_save_article)

        saveButton.setOnClickListener {
            saveContent()
        }
    }

    private fun saveContent() {
        val title = titleEditText.text.toString().trim()
        val content = contentEditText.text.toString().trim()

        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(context, "Cannot save empty content", Toast.LENGTH_SHORT).show()
            return
        }

        // Create the item - allow saving even if only one field is filled (adjust if needed)
        val newItem = MyContentItem(
            title = if (title.isNotEmpty()) title else null, // Store null if title is empty
            textContent = if (content.isNotEmpty()) content else null // Store null if content is empty
        )

        // Add to the application's list
        myApp.myContentList.add(newItem)
        Toast.makeText(context, "Content saved (Prototype)", Toast.LENGTH_SHORT).show()

        // Navigate back to the previous fragment (CreateFragment)
        parentFragmentManager.popBackStack()
    }

    companion object {
        fun newInstance(): ArticleEditorFragment {
            return ArticleEditorFragment()
        }
    }
}