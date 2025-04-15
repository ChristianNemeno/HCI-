package com.android.hci.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.hci.R

// Make sure ExploreItem is imported if it's in a different package

class ExploreAdapter(private val items: List<ExploreItem>) :
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
            Toast.makeText(holder.itemView.context, "Clicked: ${item.title}", Toast.LENGTH_SHORT).show()
            // You could navigate or do something else here
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size
}