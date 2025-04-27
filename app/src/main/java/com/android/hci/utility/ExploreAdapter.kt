package com.android.hci.utility

import android.annotation.SuppressLint // Needed for notifyDataSetChanged
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.hci.R
import com.android.hci.fragments.ExploreFragment


class ExploreAdapter(
    // MODIFIED: Made initial list accessible and mutable within the adapter scope
    private var items: List<ExploreItem>,
    private val listener: OnExploreItemClickListener
) :
    RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

    // ViewHolder holds references to the views for each item
    class ExploreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewDocument: ImageView = itemView.findViewById(R.id.image_view_document)
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val textViewDescription: TextView = itemView.findViewById(R.id.text_view_description)
    }

    // Creates new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.explore_list_item, parent, false)
        return ExploreViewHolder(view)
    }

    // Replaces the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val item = items[position]
        holder.imageViewDocument.setImageResource(item.imageResId)
        holder.textViewTitle.text = item.title
        holder.textViewDescription.text = item.description

        // Optional: Add click listener to the item
        holder.itemView.setOnClickListener {
            listener.onExploreItemClick(item)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    // --- NEW METHOD: To update the list displayed by the adapter ---
    @SuppressLint("NotifyDataSetChanged") // Using this for simplicity in prototype
    fun updateList(newList: List<ExploreItem>) {
        items = newList
        notifyDataSetChanged() // In production, use DiffUtil for better performance
    }
}