package com.example.demodatingapp.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.demodatingapp.adapter.GalleryAdapter
import com.example.demodatingapp.databinding.FragmentGalleryBinding

class GalleryFragment: Fragment() {

    private lateinit var mBinding: FragmentGalleryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentGalleryBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = GalleryFragmentArgs.fromBundle(arguments!!)
        mBinding.fullscreenGallery.mViewPager.adapter = GalleryAdapter(
            args.images.toTypedArray(),
            mBinding.root.context)
        mBinding.fullscreenGallery.mViewPager.currentItem = args.selectedIndex
    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        activity?.actionBar?.hide()
    }

    override fun onPause() {
        super.onPause()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        activity?.actionBar?.show()
    }
}