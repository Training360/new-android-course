package com.example.demodatingapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.example.demodatingapp.R

class PersonDetailGalleryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val mViewPager: ViewPager

    init {
        LayoutInflater.from(context).inflate(R.layout.view_person_details_gallery, this, true)
        mViewPager = findViewById(R.id.pager)
    }
}