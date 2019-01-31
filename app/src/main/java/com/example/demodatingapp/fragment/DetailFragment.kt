package com.example.demodatingapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.demodatingapp.adapter.GalleryAdapter
import com.example.demodatingapp.adapter.GalleryListener
import com.example.demodatingapp.databinding.FragmentDetailBinding
import com.example.demodatingapp.util.RetryCallback
import com.example.demodatingapp.viewmodel.PersonDetailViewModel
import com.example.demodatingapp.viewmodel.factory.PersonViewModelFactory

class DetailFragment: Fragment(), GalleryListener {

    lateinit var viewModel: PersonDetailViewModel

    lateinit var mBinding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mBinding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this, PersonViewModelFactory(application()))
            .get(PersonDetailViewModel::class.java)

        val personId = DetailFragmentArgs.fromBundle(arguments!!).personId
        viewModel.user.observe(this, Observer {
            mBinding.personResource = it
            if (it?.data != null) {
                mBinding.gallery.mViewPager.adapter = GalleryAdapter(it.data.galleryImages, mBinding.root.context, this)
                mBinding.personDetailHeader.binding.person = it.data
                mBinding.personDetailIntroduction.binding.person = it.data
            }
        })

        viewModel.userId = personId
        mBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                viewModel.retry()
            }
        }

        return mBinding.root
    }

    override fun onGalleryItemClicked(position: Int, imageIds: Array<String>) {
        val direction = DetailFragmentDirections.navigationToGallery(position, imageIds)
        findNavController().navigate(direction)
    }
}