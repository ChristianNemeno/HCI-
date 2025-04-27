package com.android.hci.utility

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast // Keep Toast for example
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.hci.R

// Add listener parameter to the constructor
class MyContentAdapter(
    private val contentList: List<MyContentItem>,
    private val listener: OnMyContentItemClickListener // Added listener
) : RecyclerView.Adapter<MyContentAdapter.MyContentViewHolder>() {

    class MyContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view_my_content_item)
        val textView: TextView = itemView.findViewById(R.id.text_view_my_content_item)

        // Bind function to set listener
        fun bind(item: MyContentItem, listener: OnMyContentItemClickListener) {
            itemView.setOnClickListener {
                listener.onMyContentItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyContentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_content_list_item, parent, false)
        return MyContentViewHolder(view)
    }

    override fun getItemCount(): Int = contentList.size

    override fun onBindViewHolder(holder: MyContentViewHolder, position: Int) {
        val item = contentList[position]
        holder.bind(item, listener) // Pass item and listener to bind

        // Reset visibility
        holder.imageView.visibility = View.GONE
        holder.textView.visibility = View.GONE

        // --- Safely handle image URI ---
        if (item.imageUriString != null) {
            try {
                item.imageUriString.let { uriString ->
                    val imageUri = uriString.toUri()
                    holder.imageView.visibility = View.VISIBLE
                    holder.imageView.load(imageUri) {
                        placeholder(R.drawable.ic_launcher_background)
                        error(R.drawable.ic_create)
                    }
                }
            } catch (e: Exception) {
                holder.imageView.setImageResource(R.drawable.ic_create)
                holder.imageView.visibility = View.VISIBLE
            }
        }
        // --- Handle text content ---
        else if (item.textContent != null) {
            // Show title if available, otherwise just content
            val displayText = if (!item.title.isNullOrEmpty()) {
                "${item.title}\n\n${item.textContent}" // Simple combination for list view
            } else {
                item.textContent
            }
            holder.textView.text = displayText
            holder.textView.visibility = View.VISIBLE
        }
        // Optional: Handle case where both are null
        else {
            // Maybe show title only if content and image are null?
            if (!item.title.isNullOrEmpty()) {
                holder.textView.text = item.title
                holder.textView.visibility = View.VISIBLE
            } else {
                // Fallback if truly empty
                holder.textView.text = "Empty Item"
                holder.textView.visibility = View.VISIBLE
            }
        }

        // REMOVED the old onClickListener from here, it's now in holder.bind()
    }
}