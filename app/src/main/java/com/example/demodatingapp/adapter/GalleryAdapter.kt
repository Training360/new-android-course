package com.example.demodatingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.demodatingapp.databinding.ItemGalleryBinding
import com.squareup.picasso.Picasso

interface GalleryListener {
    fun onGalleryItemClicked(position: Int, imageIds: Array<Int>)
}

class GalleryAdapter(private val imageIds: Array<Int>,
                     private val context: Context,
                     private val galleryListener: GalleryListener? = null): PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return imageIds.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(context), container, false)

        Picasso.get().load(imageIds[position]).into(binding.galleryItemImageView)

        binding.galleryItemImageView.setOnClickListener {
            galleryListener?.onGalleryItemClicked(position, imageIds)
        }

        container.addView(binding.root)

        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}