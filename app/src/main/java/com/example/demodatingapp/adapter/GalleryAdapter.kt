package com.example.demodatingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.demodatingapp.databinding.ItemGalleryBinding
import com.example.demodatingapp.util.ImageLoader

interface GalleryListener {
    fun onGalleryItemClicked(position: Int, imageIds: Array<String>)
}

class GalleryAdapter(private val imageNames: Array<String>,
                     private val context: Context,
                     private val galleryListener: GalleryListener? = null): PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return imageNames.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(context), container, false)

        ImageLoader.load(imageNames[position], binding.galleryItemImageView)

        binding.galleryItemImageView.setOnClickListener {
            galleryListener?.onGalleryItemClicked(position, imageNames)
        }

        container.addView(binding.root)

        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}