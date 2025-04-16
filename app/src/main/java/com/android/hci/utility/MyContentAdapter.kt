package com.android.hci.utility

import android.net.Uri // Import Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.hci.R

class MyContentAdapter(private val contentList: List<MyContentItem>) :
    RecyclerView.Adapter<MyContentAdapter.MyContentViewHolder>() {

    class MyContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view_my_content_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyContentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_content_list_item, parent, false)
        return MyContentViewHolder(view)
    }

    override fun getItemCount(): Int = contentList.size

    override fun onBindViewHolder(holder: MyContentViewHolder, position: Int) {
        val item = contentList[position]

        // Display the URI String in the TextView (optional)

        // Use Coil to load the image from the URI string
        try {
            val imageUri = item.imageUriString.toUri() // Convert String back to Uri
            holder.imageView.load(imageUri) {
                placeholder(R.drawable.ic_launcher_background) // Optional placeholder
                error(R.drawable.ic_create) // Optional error image
            }
        } catch (e: Exception) {
            // Handle potential errors converting string to URI or loading
            holder.imageView.setImageResource(R.drawable.ic_create) // Show error placeholder
            // Log the error e.g., Log.e("MyContentAdapter", "Error loading image URI: ${item.imageUriString}", e)
        }

        // Add onClickListener to the item if needed
        holder.itemView.setOnClickListener {
            // E.g., Toast.makeText(holder.itemView.context, "Clicked item ID: ${item.id}", Toast.LENGTH_SHORT).show()
        }
    }
}