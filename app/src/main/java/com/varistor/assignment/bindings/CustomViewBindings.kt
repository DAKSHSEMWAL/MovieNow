package com.varistor.assignment.bindings

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.varistor.assignment.R
import com.varistor.assignment.utils.Common

object CustomViewBindings {
    @JvmStatic
    @BindingAdapter("setAdapter")
    fun bindRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context,2)
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindRecyclerViewAdapter(imageView: ImageView, imageUrl: String?) {
        if (imageUrl != null) {
            // If we don't do this, you'll see the old image appear briefly
            // before it's replaced with the current image
            if (imageView.getTag(R.id.image_url) == null || imageView.getTag(R.id.image_url) != imageUrl) {
                imageView.setImageBitmap(null)
                imageView.setTag(R.id.image_url, imageUrl)
                Glide.with(imageView).load(imageUrl).into(imageView)
            }
        } else {
            imageView.setTag(R.id.image_url, null)
            imageView.setImageBitmap(null)
        }
    }
}